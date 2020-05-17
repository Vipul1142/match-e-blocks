#!/bin/bash
javac ./MatchBlocks.java
jar cmf MatchBlocks.mf MatchBlocks.jar *.class images/*
java -jar MatchBlocks.jar