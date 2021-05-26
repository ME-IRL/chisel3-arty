package dev.meirl.chisel3arty.examples

import chisel3._
import dev.meirl.chisel3arty.ArtyBoard
import main.Blinky

class ArtyTop extends ArtyBoard {
  withClockAndReset(CLK100MHZ, !ck_rst.asBool){
    val blinky = Module(new Blinky(1))
    led(0) := blinky.io.out
  }

}
