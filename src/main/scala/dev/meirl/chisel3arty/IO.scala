package dev.meirl.chisel3arty

import chisel3._
import chisel3.experimental.{Analog, IntParam, StringParam}
import chisel3.internal.firrtl.Width

/** Refer to Xilinx UG953 (pg 200) **/
class IOBUF (
  width      : Width  = 1.W,
  DRIVE      : Int    = 12,
  IOSTANDARD : String = "DEFAULT",
  SLEW       : String = "SLOW"
) extends BlackBox (
  Map(
    "DRIVE"      -> IntParam(DRIVE),
    "IOSTANDARD" -> StringParam(IOSTANDARD),
    "SLEW"       -> StringParam(SLEW)
  )
) {
  val io = IO(new Bundle {
    val IO = Analog(width)
    val I  = Input(UInt(width))
    val O  = Output(UInt(width))
    val T  = Input(Bool())
  })
}

//object IOBUF {
//  def apply(
//    width      : Int    = 1,
//    DRIVE      : Int    = 12,
//    IOSTANDARD : String = "DEFAULT",
//    SLEW       : String = "SLOW"
//  ) = {
//    for ( _ <- 0 until width ) yield {
//      Module(new IOBUF(DRIVE, IOSTANDARD, SLEW))
//    }
//  }
//}