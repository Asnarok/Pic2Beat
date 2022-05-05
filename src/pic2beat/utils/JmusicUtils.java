package pic2beat.utils;

import jm.midi.MidiSynth;
import jm.util.Play;

import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import java.lang.reflect.Field;

public class JmusicUtils {

    /**
     * Use instead of Play.stopMidi() which causes a StackOverflowError due to a recursive call
     */
    public static void stopMidi() {
        try {
            Field midiSynthF = Play.class.getDeclaredField("currentMidiSynth");
            midiSynthF.setAccessible(true);
            MidiSynth midiSynth = (MidiSynth) midiSynthF.get(null);
            if(midiSynth != null) {
                midiSynth.stop();

                Field f = MidiSynth.class.getDeclaredField("m_seq");
                f.setAccessible(true);
                f.set(midiSynth, null);

                f = MidiSynth.class.getDeclaredField("m_sequencer");
                f.setAccessible(true);
                ((Sequencer) f.get(midiSynth)).close();

                f = MidiSynth.class.getDeclaredField("m_synth");
                f.setAccessible(true);
                ((Synthesizer) f.get(midiSynth)).close();
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
