[![License](https://img.shields.io/badge/license-CeCILL--B-orange)](http://www.cecill.info/licences.fr.html)

# Pic2Beat
Pic2Beat permet de générer une musique de zéro, des percussions à la mélodie.

## Contenu
### Les packages
* _pic2beat_, contenant le programme principal et contenant :
    + _harmonia_, contenant l'algorithme de génération d'accords
        - _rhythms_, contenant les enfants de _ChordRhythm.java_
    + _melodia_, contenant l'algorithme de génération de mélodie
        - _rhythms_, contenant les enfants de _Rhythm.java_
    + _res_, contenant des ressources (images, ...)
        - _generators_, contenant les enfants de _SongGenerator.java_
    + _song_, contenant la description de la structure du morceau
    + _ui_, contenant l'IHM
    + _utils_, regroupant diverses fonctions utiles

### Les classes
* _Chord.java_ représente un accord
* _HarmonIA.java_, algorithme de génération de suites d'accords
* _MelodIA.java_, algorithme de génération de mélodie
* _Rhythm.java_ et toutes les classes filles, représentent des rythmes
* _ChordRhythm.java_ et toutes les classes filles, représentent des rythmes d'accord
* _Song.java_ représente la morceau
* _SongPart.java_ représente une partie du morceau
* _SongGenerator.java_ et toutes les classes filles, représentent une manière de générer le morceau
* _InstrumentRole.java_ représente le role d'un instrument
* _ComposerFrame.java_ représente la fenêtre
* _ChorusPanel.java_, _IntroPanel.java_, _SongPartPanel.java_, _VersePanel.java_, _ChordsPanel.java_, _NoteLabelPanel.java_, _ScorePane.java_ et _NotePanel.java_ sont des enfants de _JPanel_
* _NoteScrollPane.java_ est un enfant de _JScrollPane_
* _FileUtils.java_, _JsonChordParser.java_, _MathUtils.java_, _JmusicUtils.java_ et _Scales.java_ sont des classes inclassables et utilitaires
* _Main.java_ est la classe principale du programme

### Les ressources

* _chords.json_, la base de données pour les probabilités des progressions d'accords
* _assets_, contenant les ressources
    - _images_, contenant les images
    - _instruments_, contenant les listes des instruments disponibles

## Dépendances

| Librairie | Lien | Version |
| ------ | ------ | ------ |
| Gson | https://github.com/google/gson | 2.9.0 |
| Jmusic | https://explodingart.com/jmusic/ | 1.6.5 |

## Licence

Voir [LICENSE](https://github.com/Asnarok/Pic2Beat/blob/master/LICENSE)

## Version

> 1.0

## Auteurs

* [@Asnarok](https://github.com/Asnarok)
* [@Arhtourrr](https://github.com/Arthourrr)
* [@ZozoKngg](https://github.com/ZozoKngg)
* [@pjsph](https://github.com/pjsph)