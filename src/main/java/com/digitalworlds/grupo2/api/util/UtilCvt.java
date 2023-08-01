package com.digitalworlds.grupo2.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class UtilCvt {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static String strORConvert(Integer[] arrayInteger) {
        //convert [28, 53] to "28|53"
        String stringOR = null;
        if (arrayInteger != null && arrayInteger.length > 0) {
            List<Integer> listInteger = Arrays.stream(arrayInteger).collect(Collectors.toList());
            Collections.sort(listInteger);
            stringOR = "";
            for (Integer idGenre : listInteger) {
                stringOR += "|" + idGenre;
            }
            stringOR = stringOR.substring(1);
        }
        return stringOR;
    }

    public static Integer[] strORConvert(String stringOR) {
        //convert "28|53" to [28, 53]
        Integer[] arrayInteger = null;
        if (stringOR != null && !stringOR.trim().isEmpty()) {
            String[] ESelectedGenres = stringOR.split(Pattern.quote("|"));
            List<Integer> listDTOSelectedGenres = new ArrayList<>();
            for (int i = 0; i < ESelectedGenres.length; i++) {
                listDTOSelectedGenres.add(Integer.parseInt(ESelectedGenres[i]));
            }
            arrayInteger = new Integer[listDTOSelectedGenres.size()];
            listDTOSelectedGenres.toArray(arrayInteger);
        }
        return arrayInteger;
    }
}
