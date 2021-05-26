package dev.meirl.chisel3arty

import chisel3._
import chisel3.experimental.{Analog, IntParam, StringParam}
import chisel3.internal.firrtl.Width
import chisel3.util.HasBlackBoxInline

/** Refer to Xilinx UG953 (pg 200) **/

/** IDK how to make it do instance array
 * (example https://stackoverflow.com/a/1380171)
 * Will have to manually add that into generated verilog?
 *
 * Another option is to have each inout pin separated as
 * individual ports
 *   eg. Analog(n.W) -> [ Analog(1.W), Analog(1.W), ... repeated N times ]
 * then, we can use 
 **/
class IOBUF (
  width      : Width  = 1.W,
  DRIVE      : Int    = 12,
  IOSTANDARD : String = "DEFAULT",
  SLEW       : String = "SLOW"
) extends BlackBox ( Map(
  "DRIVE"      -> IntParam(DRIVE),
  "IOSTANDARD" -> StringParam(IOSTANDARD),
  "SLEW"       -> StringParam(SLEW)
)) {
  val io = IO(new Bundle {
    val IO = Analog(width)
    val I  = Input(UInt(width))
    val O  = Output(UInt(width))
    val T  = Input(Bool())
  })
}

object IOBUF {
  def apply(width: Width  = 1.W) = Module(new IOBUF(width))
  def apply(pin: Analog) = {
    val iobuf = Module(new IOBUF(pin.getWidth.W))
    iobuf.io.IO <> pin
    iobuf
  }
}

/*
// Would this work?
// May not work because inout is define in a submodule within top module
class IOBUF_infer (width: Width  = 1.W) extends BlackBox with HasBlackBoxInline {
  val io = IO(new Bundle {
    val IO = Analog(width)
    val I  = Input(UInt(width))
    val O  = Output(UInt(width))
    val T  = Input(Bool())
  })

  // Actually, it won't work since I'd have to customize
  // the module with different widths
  setInline("IOBUF.v",
    s"""module IOBUF_infer (
       |  inout  [15:0] IO,
       |  input  [15:0] I,
       |  output [15:0] O,
       |  input         T
       |);
       |
       |assign O := IO;
       |assign IO := I ? T : 16'bZZZZ_ZZZZ_ZZZZ_ZZZZ;
       |
       |endmodule
       |""".stripMargin)
}

object IOBUF_infer {
  def apply(width: Width  = 1.W) = Module(new IOBUF_infer(width))
  def apply(pin: Analog) = {
    val iobuf = Module(new IOBUF_infer(pin.getWidth.W))
    iobuf.io.IO <> pin
    iobuf
  }
}
*/