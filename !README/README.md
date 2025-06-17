# Blackjack als Spiel, für ein Schulprojekt 

## Das Spiel wurde mit Java und Swing-GUI programmiert 

Ein einfaches Blackjack-Spiel in Java mit Swing‑GUI. Der Spieler tritt gegen einen Dealer an, kann Einsätze platzieren, Karten ziehen („Hit“) oder passen („Stand“). Man sieht seinen Kontostand sowie das Ergebnis am Ende jeder Runde.

Features:

- Einsatzverwaltung: Startguthaben, Einsatz setzen, Gewinnen/Verlieren
- Interaktive Buttons: „Setzen“, „Hit“, „Stand“, „New Game“, „Exit“
- Animierte End-Dialoge: Zähleranimation für Gewinn oder Verlust
- Ansprechende GUI: Kartenlisten, Statusanzeige, individueller Hintergrund

Voraussetzungen:

- Java 8 oder höher
- IDE (z. B. IntelliJ IDEA, Eclipse) oder Konsole mit javac/java

Installation & Ausführung:

- Projekt in IDE importieren oder Quellcode lokal ablegen.
- In der Konsole im Projekt‑Ordner:
- javac blackjack/*.java
- java blackjack.BlackjackGUI
- In der IDE einfach die Klasse BlackjackGUI als Hauptklasse starten.

Kurzanleitung: 

- Ziel: Mit deinen Karten möglichst nah an 21 Punkte kommen, aber nicht darüber (Bust).
- Kartenwerte: 2–10 = Zahlenwert, Bube/Dame/König = 10, Ass = 1 oder 11 (je nachdem, was günstiger ist).

Ablauf:

- Einsatz eingeben und auf Setzen klicken.
- Zwei Karten werden dir und dem Dealer ausgeteilt (eine Dealer‑Karte bleibt verdeckt).
- Mit Hit nimmst du weitere Karten, bis du „Stand“ wählst oder „Bust“ gehst (> 21).
- Anschließend spielt der Dealer nach festen Regeln weiter (zieht bis mindestens 17).
- Gewinner ist, wer näher an 21 ist, ohne darüber zu gehen. Bei „Blackjack“ (21 mit zwei Karten) gibt’s 1,5× Auszahlung.
- Rundenende: Ergebnis und neuer Kontostand werden angezeigt. Dann New Game, um erneut zu spielen, oder Exit, um das Programm zu beenden.

## Notes

- javac -encoding UTF-8 -d bin -sourcepath src src/blackjack/BlackjackGUI.java
- java -cp bin blackjack.BlackjackGUI

## Verbesserungen:

- Guthaben Grün oder Rot darstellen, je nach dem ob man im Plus oder Minus ist
- Deal Animationen, Karten Animationen
- Weitere Funktionen (Split, double)
- Meine GUI Klasse in eine weitere Datei aufteilen, für mehr übersicht
- Kommentare Minimieren und nur eigene Einfügen, wenn ich mir den Code erklären lasse
- Möglichebin/images/blackjack-title.png

