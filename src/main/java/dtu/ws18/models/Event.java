package dtu.ws18.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Event implements Serializable {
 public Event (EventType type, Object object){
  this.object = object;
  this.type = type;

 }
 EventType type;
 Object object;

}
