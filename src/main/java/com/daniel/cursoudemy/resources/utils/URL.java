package com.daniel.cursoudemy.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    public static String decodeParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
           return "";
        }
    }


    public static List<Integer> decodeIntList(String s) {

        List<Integer> list = new ArrayList<>();
        // Split quebra a String em pedaços de acordo com caracter passado
        String[] vet = s.split(",");
        for (int i = 0; i < vet.length; i++) {
            list.add(Integer.parseInt(vet[i]));
        }

        return list;
        // Outra Alternativa com stream
        //return Arrays.asList(s.split(",")).stream().map(x->Integer.parseInt(x)).collect(Collectors.toList());
    }

}
