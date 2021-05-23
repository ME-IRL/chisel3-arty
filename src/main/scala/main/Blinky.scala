package main

import chisel3._
import chisel3.util.Counter

class BlinkyIO extends Bundle {
  val out = Output(Bool())
}

class Blinky(freq: Int)(implicit val boardClockFrequency: Int) extends Module {
  //  def log2 = (x: Int) => ((log10(x)/log10(2.0)).round + 1).toInt
  val io = IO(new BlinkyIO)

  val status = RegInit(false.B)
  io.out := status

  val (_, toggle) = Counter(true.B, boardClockFrequency/2)
  when(toggle){
    status := ~status
  }
}
