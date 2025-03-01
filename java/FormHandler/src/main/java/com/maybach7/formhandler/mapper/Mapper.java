package com.maybach7.formhandler.mapper;

public interface Mapper<T, F> {

    T mapFrom(F f);
}
