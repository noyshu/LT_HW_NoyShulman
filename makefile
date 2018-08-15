JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
        Boundry.java \
        Image.java \
        HoleFiller.java \
        Main.java \
        ImageGenerator.java \
        Location.java \
        WeightFunction.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class