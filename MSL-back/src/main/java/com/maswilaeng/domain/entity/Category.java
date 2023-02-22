package com.maswilaeng.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public enum Category {
    RECIPE,
    BAR_SNACK,
    FREE
}

