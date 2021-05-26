package dev.meirl.chisel3arty.examples

import chisel3._
import chisel3.experimental.Analog

abstract class GBABoard extends RawModule {

  implicit val boardClockFrequency : Int = 100000000

  // Clock Signal
  val CLK100MHZ = IO(Input(Clock()))

  // Switches
  val sw = IO(Input(UInt(4.W)))

  // LEDs
  val led = IO(Output(UInt(4.W)))
  led := DontCare

  // Buttons
  val btn = IO(Input(UInt(4.W)))

  // GBA Interface
  val GBA_CLK = IO(Input(Bool()))
  val GBA_nWR = IO(Input(Bool()))
  val GBA_nRD = IO(Input(Bool()))
  val GBA_nCS = IO(Input(Bool()))
  val GBA_AD  = IO(Analog(16.W))
  val GBA_A   = IO(Analog(8.W))
  val GBA_nCS2 = IO(Input(Bool()))
  val GBA_nREQ = IO(Output(Bool()))
  val GBA_VDD  = IO(Input(Bool()))
  GBA_AD   := DontCare
  GBA_A    := DontCare
  GBA_nREQ := DontCare

  // USB-UART Interface
  val uart_rxd_out = IO(Output(Bool()))
  val uart_txd_in  = IO(Input(Bool()))
  uart_rxd_out := DontCare

  // Debug port
  val debug = IO(Output(UInt(8.W)))
  debug := DontCare

  // SD Card
  val spi_cs   = IO(Output(Bool()))
  val spi_mosi = IO(Output(Bool()))
  val spi_miso = IO(Input(Bool()))
  val spi_sck  = IO(Output(Bool()))
  spi_cs   := DontCare
  spi_mosi := DontCare
  spi_sck  := DontCare

  // Misc. ChipKit Ports
  val ck_rst = IO(Input(Bool()))
}