package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTokenResponseDto {
    private String token;

    @Builder
}
