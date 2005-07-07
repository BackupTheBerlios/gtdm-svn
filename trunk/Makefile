#
# Makefile for GTDManager (SoPra05 Gruppe 6)
#
#	by Tomislav Viljetić
#

AUTHOR		= Tomislav Viljetić

MODULE		= GTDManager

SRCDIR		= src
CLASSPATH	= $(SRCDIR):freehep-base.jar:freehep-graphics2d.jar:freehep-graphicsip.jar

CORENAME	= gtdmanager/core
CORESOURCES	= $(wildcard $(SRCDIR)/$(CORENAME)/*.java)
CORECLASSES	= $(CORESOURCES:%.java=%.class)

GUINAME		= gtdmanager/gui
GUISOURCES	= $(wildcard $(SRCDIR)/$(GUINAME)/*.java)
GUICLASSES	= $(GUISOURCES:%.java=%.class)


CLASSES		= $(GUICLASSES) $(CORECLASSES)

%.class: %.java
	@echo === JAVAC $<
	@javac -classpath $(CLASSPATH) $<

%.manifest: meta/manifest/%
	@echo === MANIFEST $*
	@sed	-e "s,%Author%,$(AUTHOR),g"\
		-e "s,%Main-Class%,$*,g"\
		-e "s/%Date%/$(shell date -R)/g" $<>$@

all: jar-test
#	@echo nothing to do, please specify some target..

$(MODULE).jar: $(MODULE).manifest $(CLASSES)
	@echo === JAR $@
	jar cvfm $@ $< \
	    $(subst $(SRCDIR)/,-C $(SRCDIR) ,$(wildcard $(SRCDIR)/*/*/*.class))

jar-test: $(MODULE).jar
	#clean $(MODULE).jar
	@echo === JAVA $(MODULE).jar
	@LC_ALL=C java -jar $(MODULE).jar


#test: $(CORECLASSES)
#	@echo === make test
#	@LC_ALL=C java -cp $(CLASSPATH) $(CORENAME).RunClass
#
#ctags: ${CORECLASSES}
#	ctags -R -f tags $^

clean: .
	@echo === make clean
	@rm -vf GTDM*.manifest GTDM*.jar $(SRCDIR)/*/*/*.class
