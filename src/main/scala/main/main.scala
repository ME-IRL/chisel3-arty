package main

import chisel3.stage.{ChiselGeneratorAnnotation, ChiselStage}

object main extends App {
  val path = try args(0) catch { case _: Throwable => "generated_verilog" }

  val chiselArgs = Array("-E", "verilog", "-td", path)
  (new ChiselStage).execute(chiselArgs, Seq(ChiselGeneratorAnnotation(() => new ArtyTop())))
}
