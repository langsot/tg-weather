package com.github.langsot.tgweather;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Responce implements Comparable<Responce> {

    private Long chatId;
    private String city;
    private Integer time;

    @Override
    public int compareTo(Responce o) {
        return time.compareTo(o.getTime());
    }
}
