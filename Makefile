.PHONY: synth generate clean

synth:
	fusesoc --cores-root . run meirlabs::chisel3-arty

gba:
	fusesoc --cores-root . run --target=$@ meirlabs::chisel3-arty

generate:
	sbt run

clean:
	rm -rf build generated_verilog
