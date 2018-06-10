package me.grax.genderswap.scales;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import me.grax.genderswap.scales.Scales.Scale;

public class ScaleIdentifier {

  private Track t;

  public ScaleIdentifier(Track t) {
    this.t = t;
  }

  public Scale findScale() {
    HashMap<Scale, Integer> score = new LinkedHashMap<>();
    for (Scale s : Scales.scales) {
      score.put(s, 0);
    }
    for (int i = 0; i < t.size(); i++) {
      MidiEvent e = t.get(i);
      if (e.getMessage() instanceof ShortMessage) {
        ShortMessage shortMessage = (ShortMessage) e.getMessage();

        if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
          int key = shortMessage.getData1();
          int vel = shortMessage.getData2();
          if (vel > 0 && key > -1) {
            for (Scale s : Scales.scales) {
              if (s.isOnScale(key)) {
                score.put(s, score.get(s) + 1);
              } else {
                score.put(s, score.get(s) - 1);
              }
            }
          }
        }
      }
    }
    int max = 0;
    Scale s = null;
    for(Entry<Scale, Integer> e : score.entrySet()) {
      if(e.getValue() > max) {
        max = e.getValue();
        s = e.getKey();
      }
    }
    return s;
  }

}
