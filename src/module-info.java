module module.info {
	exports dao;
	exports test;
	exports util;
	exports myexceptions;
	exports main;
	exports entity;

	requires java.sql;
	requires junit;
	requires org.junit.jupiter.api;
}