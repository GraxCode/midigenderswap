package me.grax.genderswap;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import me.grax.genderswap.scales.ScaleIdentifier;
import me.grax.genderswap.scales.Scales;
import me.grax.genderswap.scales.Scales.Scale;

public class MajorMinor {

  public static void main(String[] args) throws InvalidMidiDataException {
    if (args.length < 2) {
      System.err.println("Please specify input and output file");
      return;
    }
    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);
    Sequence sequence = null;
    try {
      sequence = MidiSystem.getSequence(inputFile);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    int index = 0;
    for (Track t : sequence.getTracks()) {
      Scale s = new ScaleIdentifier(t).findScale();
      if (s != null) {
        int changedKeys = 0;
        System.out.println("Track " + index + " is using Scale " + s.getName() + " (" + (s.getType() == 0 ? "Major" : "Minor") + ")");
        Scale newScale = switchMajorMinor(s);
        for (int i = 0; i < t.size(); i++) {
          MidiEvent e = t.get(i);
          if (e.getMessage() instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) e.getMessage();
            if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
              int channel = shortMessage.getChannel();
              int key = shortMessage.getData1();
              int vel = shortMessage.getData2();
              if (vel > 0 && key > -1) {
                if (s.isOnScale(key)) {
                  while (!newScale.isOnScale(key)) {
                    key--;
                    shortMessage.setMessage(shortMessage.getCommand(), channel, key, vel);
                    changedKeys++;
                  }
                }
              }
            }
          }
        }
        System.out.println("Changed " + changedKeys + " keys on track " + index);
      }
      index++;
    }

    try {
      MidiSystem.write(sequence, MidiSystem.getMidiFileTypes(sequence)[0], outputFile);
      System.out.println("Swapped major and minor successfully!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Scale switchMajorMinor(Scale midiScale) {
    for (Scale s : Scales.scales) {
      if (s.getName().equals(midiScale.getName()) && s.getType() != midiScale.getType()) {
        return s;
      }
    }
    throw new IllegalArgumentException();
  }
}
