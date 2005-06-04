#
# Makefile for GTDManager (SoPra05 Gruppe 6)
#
#	by Tomislav Viljetić
#

SRCDIR		= src
CLASSPATH	= $(SRCDIR)

CORENAME	= gtdmanagercore
CORESOURCES	= $(wildcard $(SRCDIR)/$(CORENAME)/*.java)
CORECLASSES	= $(CORESOURCES:%.java=%.class)

%.class: %.java
	@echo === JAVAC $<
	@javac -classpath $(CLASSPATH) $<

%.manifest: $(SRCDIR)/%/manifest
	@echo === MANIFEST $*
	@sed	-e "s,%Main-Class%,$*.RunClass,g"\
		-e "s/%Date%/$(shell date -R)/g" $<>$@

%.jar: %.manifest $(CORECLASSES)
	@echo === JAR $@
	@jar cvfm $@ $*.manifest $(CORECLASSES:$(SRCDIR)/%=-C $(SRCDIR) %)

jar: $(CORENAME).jar

test: ${CORECLASSES}
	@echo === make test: $^
	@LC_ALL=C java $(basename ${CORETESTJAR} .jar)

test-jar: ${CORENAME}.jar
	@echo === JAVA $<
	@LC_ALL=C java -jar $<

ctags: ${CORECLASSES}
	ctags -R -f tags $^

clean:
	@echo === make clean
	@rm -vf $(CORENAME).manifest $(CORENAME).jar $(CORECLASSES)
