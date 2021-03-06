package main

import chisel3.stage.{ChiselGeneratorAnnotation, ChiselStage}
import dev.meirl.chisel3arty.examples.{ArtyTop, GBATop}

object main extends App {
  val path = try args(0) catch { case _: Throwable => "generated_verilog" }

  val chiselArgs = Array("-E", "verilog", "-td", path)
  (new ChiselStage).execute(chiselArgs, Seq(ChiselGeneratorAnnotation(() => new ArtyTop())))
  (new ChiselStage).execute(chiselArgs, Seq(ChiselGeneratorAnnotation(() => new GBATop())))
}
