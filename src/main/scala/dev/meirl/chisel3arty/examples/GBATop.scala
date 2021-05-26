package dev.meirl.chisel3arty.examples

import Chisel.Cat
import chisel3._
import chisel3.experimental.{ChiselAnnotation, annotate}
import chisel3.util.Fill
import chisel3.util.experimental.loadMemoryFromFileInline
import dev.meirl.chisel3arty.IOBUF
import firrtl.annotations.MemorySynthInit
import main.Blinky

class GBATop extends GBABoard {
  withClockAndReset(CLK100MHZ, !ck_rst.asBool){
    val blinky = Module(new Blinky(1))
    led := Fill(4, blinky.io.out)

    uart_rxd_out := uart_txd_in

    val GBA_AD_in = Wire(UInt(16.W))
    val GBA_AD_out = Wire(UInt(16.W))
    val GBA_AD_oe = Wire(UInt(1.W))

    val ad_buf = Module(new IOBUF(16.W))
    ad_buf.io.IO <> GBA_AD
    ad_buf.io.I <> GBA_AD_out
    ad_buf.io.O <> GBA_AD_in
    ad_buf.io.T <> !GBA_AD_oe

    val GBA_A_in = Wire(UInt(8.W))
    val GBA_A_out = Wire(UInt(8.W))
    val GBA_A_oe = Wire(UInt(1.W))

    val a_buf = Module(new IOBUF(8.W))
    a_buf.io.IO <> GBA_A
    a_buf.io.I <> GBA_A_out
    a_buf.io.O <> GBA_A_in
    a_buf.io.T <> !GBA_A_oe

    GBA_A_oe := !GBA_nCS2 && !GBA_nRD && GBA_VDD
    GBA_AD_oe := !GBA_nCS && !GBA_nRD && GBA_VDD

    val resyncRD = RegInit(0.U(2.W))
    val resyncCS = RegInit(0.U(2.W))
    val resyncWR = RegInit(0.U(2.W))
    when(true.B){
      resyncRD := Cat(resyncRD(0), GBA_nRD)
      resyncCS := Cat(resyncCS(0), GBA_nCS)
      resyncWR := Cat(resyncCS(0), GBA_nWR)
    }

    val rRD = !resyncRD(1) && resyncRD(0)
    val fCS = resyncCS(1) && !resyncCS(0)
    val fWR = resyncWR(1) && !resyncWR(0)

    val rom_mem = Mem(610, UInt(16.W))
    val ram_mem = Mem(8, UInt(8.W))

    /** This annotation not yet in release
     *  Should be in the next one
     *  (Using using FIRRTL/CHISEL version 3.5-SNAPSHOT)
     **/
    annotate(new ChiselAnnotation {
      override def toFirrtl = MemorySynthInit
    })
    loadMemoryFromFileInline(rom_mem, "fire.mem")

    val rom_addr_low = Reg(UInt(16.W))
    GBA_AD_out := rom_mem.read(rom_addr_low)
    GBA_A_out := ram_mem.read(GBA_A_in(3,0))

    when(!GBA_nCS && rRD){
      rom_addr_low := rom_addr_low + 1.U
    }.elsewhen(fCS){
      rom_addr_low := GBA_AD_in
    }

    when(!GBA_nCS2 && fWR){
      ram_mem.write(GBA_AD_in(3,0), GBA_A_in)
    }
  }
}
