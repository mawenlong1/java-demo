package com.mwl.webflux.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author mawenlong
 * @date 2018/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event")
public class MyEvent {
    @Id
    private Long id;
    private String message;
}
