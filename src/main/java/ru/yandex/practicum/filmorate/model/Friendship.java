package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {

    private Long userId;

    private Long friendId;

    private LocalDateTime createdAt;

}
