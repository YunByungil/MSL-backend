package Maswillaeng.MSLback.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
public enum Category {
    RECIPE,
    COCKTAIL,
    ETC
}