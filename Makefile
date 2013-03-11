FILES := 	SqlException \
			util/TableBuilder \
			util/SetUtils \
			Schema \
			data/Datum \
			plan/ExprTree \
			plan/PlanNode \
			plan/ProjectionNode \
			plan/UnionNode \
			plan/JoinNode \
			plan/ScanNode \
			plan/SelectionNode \
			plan/NullSourceNode \
			plan/AggregateNode \
			Sql \
			test/TestHarness

include Makefile.testspec

SRC := $(patsubst %, src/%.java, $(FILES))

OBJ := $(patsubst %, build/edu/buffalo/cse/sql/%.class, $(FILES))

FLAGS := -Xlint:unchecked

all: build $(patsubst %, src/%.java, $(CC_FILES)) $(OBJ)

build:
	mkdir build

clean:
	rm -r build

build/edu/buffalo/cse/sql/%.class: src/%.java
	javac -cp build -d build $(FLAGS) $<

test: all
	@echo "Starting Test (Running to first failure)"
	@echo "Relational Algebra Execution Tests"
	@for i in $(TEST_CLASSES); do \
		for j in src/test/$$i*.java; do \
			if java -cp build edu.buffalo.cse.sql.test.$$(basename $$j .java); \
			then echo; else exit -1; fi \
	done; done

fulltest: all
	@echo "Starting Test (Running to completion)"
	@echo "Relational Algebra Execution Tests"
	@for i in $(TEST_CLASSES); do \
		for j in src/test/$$i*.java; do \
			java -cp build edu.buffalo.cse.sql.test.$$(basename $$j .java); \
	done; done