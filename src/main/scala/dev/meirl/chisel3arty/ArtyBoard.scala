package dev.meirl.chisel3arty

import chisel3._
import chisel3.experimental.Analog

abstract class ArtyBoard extends RawModule {

  implicit val boardClockFrequency : Int = 100000000

  // Clock Signal
  val CLK100MHZ = IO(Input(Clock()))

  // Switches
  val sw = IO(Input(Vec(4, Bool())))

  // RGB LEDs
  val led0_b = IO(Output(Bool()))
  val led0_g = IO(Output(Bool()))
  val led0_r = IO(Output(Bool()))
  val led1_b = IO(Output(Bool()))
  val led1_g = IO(Output(Bool()))
  val led1_r = IO(Output(Bool()))
  val led2_b = IO(Output(Bool()))
  val led2_g = IO(Output(Bool()))
  val led2_r = IO(Output(Bool()))
  val led3_b = IO(Output(Bool()))
  val led3_g = IO(Output(Bool()))
  val led3_r = IO(Output(Bool()))
  led0_b := DontCare
  led0_g := DontCare
  led0_r := DontCare
  led1_b := DontCare
  led1_g := DontCare
  led1_r := DontCare
  led2_b := DontCare
  led2_g := DontCare
  led2_r := DontCare
  led3_b := DontCare
  led3_g := DontCare
  led3_r := DontCare

  // LEDs
  val led = IO(Output(Vec(4, Bool())))
  led := DontCare

  // Buttons
  val btn = IO(Input(Vec(4, Bool())))

  // Pmod Headers
  val ja = IO(Vec(8, Analog(1.W)))
  val jb = IO(Vec(8, Analog(1.W)))
  val jc = IO(Vec(8, Analog(1.W)))
  val jd = IO(Vec(8, Analog(1.W)))
  ja := DontCare
  jb := DontCare
  jc := DontCare
  jd := DontCare

  // USB-UART Interface
  val uart_rxd_out = IO(Output(Bool()))
  val uart_txd_in  = IO(Input(Bool()))
  uart_rxd_out := DontCare

  // ChipKit Digital Header
  val ck_io = IO(Vec(42, Analog(1.W)))
  ck_io := DontCare

  // ChipKit Analog Header

  // ChipKit SPI
  val ck_miso = IO(Analog(1.W))
  val ck_mosi = IO(Analog(1.W))
  val ck_sck  = IO(Analog(1.W))
  val ck_ss   = IO(Analog(1.W))
  ck_miso := DontCare
  ck_mosi := DontCare
  ck_sck  := DontCare
  ck_ss   := DontCare

  // ChipKit I2C
  val ck_scl     = IO(Analog(1.W))
  val ck_sda     = IO(Analog(1.W))
  val scl_pup = IO(Output(Bool()))
  val sda_pup = IO(Output(Bool()))
  ck_scl     := DontCare
  ck_sda     := DontCare
  scl_pup := DontCare
  sda_pup := DontCare

  // Misc. ChipKit Ports
  val ck_ioa = IO(Analog(1.W))
  val ck_rst = IO(Input(Bool()))
  ck_ioa := DontCare

  // SMSC Ethernet PHY

  // Quad SPI Flash
  val qspi_cs = IO(Output(Bool()))
  val qspi_dq = IO(Vec(4, Analog(1.W)))
  qspi_cs := DontCare
  qspi_dq := DontCare

  // Power Measurements

}
