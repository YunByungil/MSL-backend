package Maswillaeng.MSLback.domain.entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    private Long id;
}
