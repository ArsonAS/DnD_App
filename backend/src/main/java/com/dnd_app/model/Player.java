package com.dnd_app.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@Data
@NoArgsConstructor
public class Player extends User{
}
