
.PHONY: all clean

all: Main.java clean
	javac *java && java Main sampleInput

clean:
	rm -f *.class || true
