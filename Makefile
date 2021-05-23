.PHONY: synth generate clean

synth:
	fusesoc --cores-root . run meirlabs::chisel3-arty

generate:
	sbt run

clean:
	rm -rf build generated_verilog
