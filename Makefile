compile:
	javac -d bin/ src/*.java

tilde:
	rm -f *~
	rm -f src/*~
	
clean: tilde
	rm -f bin/*.class
	rm -f pkg/*.tar.gz
	rm -rf docs/*

test: compile
	cd bin && java MessagePubTest
	
package: clean compile
	tar -czvvf pkg/messagepub.tar.gz src/ README.md bin/ Makefile docs/

docs: clean compile
	javadoc -d docs/ src/*




