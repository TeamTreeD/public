# Unser _TreeD_ Weihnachtswettbewerb

Deine Kinder fragen Dich, warum auf Deinem Schreibtisch im Frühherbst leere Packungen der wieder in den Supermärkten erhältlichen Weihnachtsleckereien liegen und Du hast keine Antwort? Dein Lieblingsmensch wünscht sich jedes Jahr zu Weihnachten mehr Romantik von Dir und Du hast keine Idee, was das bedeuten könnte?

Wir liefern Dir die Ausrede für Deine Kinder und die Idee, wie Du Deine Nerd-Spielereien zu Weihnachten familiär verkaufen kannst: unseren _TreeD_-Weihnachtswettbewerb!

Die Grundidee: der JUG Saxony e.V. hat einen Weihnachtsbaum mit digitalen LEDs ausgerüstet und stellt euch eine Programmierschnittstelle zur Verfügung, mit der ihr den Baum mit Leben erfüllen bzw. zum Leuchten bringen könnt. Am 24. November 2022 schauen wir uns - hoffentlich gemeinsam - eure Ergebnisse an und vergeben Preise für die "besten" Beleuchtungsprogramme ("Strategien").

## Teilnahmebedingungen

Es gibt zwei Wege an unserem Wettbewerb teilzunehmen: am einfachsten ist die Teilnahme an unserer Challenge auf Entwicklerheld [1] - dort könnt ihr sofort eine Strategie erstellen und mit unserem Web-Simulator auch anschauen, wie das ganze wohl aussehen wird.

Wenn ihr tiefer einsteigen wollt, solltet ihr euch unser TreeD-Repository auf github anschauen [2]. Dort findet ihr alles - die Schnittstelle inkl. Simulatoren für das Web und für JavaFX.

Die Abgabe muss bis zum 14. November 2022 erfolgen. Mehrfacheinreichungen sind möglich. Auf Entwicklerheld kann man sein Ergebnis nach der Teilnahme an einer Challenge nur einmal einreichen ("Stage abgeben") - wer demgegenüber direkt mit dem TreeD-Repository arbeitet, kann uns mehrere Strategien als Java 8-Source-File per email an treed@jugsaxony.org zusenden.

Bei beiden Varianten gilt: es dürfen nur die im JDK enthaltenen öffentlich dokumentierten Klassen (von JavaFX nur die Matrixfunktionen) verwendet werden. Und sorry: bitte kein I/O und kein Multithreading. Passt Dein Code nicht in eine Datei, solltest Du mit inneren Klassen arbeiten (siehe oben: *ein* Java 8-Source-File).

## Abschlussveranstaltung

Am 24. November 2022 wird der Wettbewerb abgeschlossen. Ob wir uns an diesem Termin in Dresden treffen und uns die Strategien live ansehen können hängt von mehreren Faktoren ab, z.B. von der Anzahl der Einsendungen und von eventuellen Corona-Beschränkungen.

Fest steht, dass in die Bewertung der Einsendungen das Feedback aller Teilnehmer eingehen wird. Neben der optischen Begutachtung werden wir zu jeder Einsendung technische Daten wie den Resourcenverbrauch veröffentlichen (welche genau, geben wir noch bekannt).

*TODO* Falk: Preise

## Datenschutz

Die Lösungen werden weder von uns noch durch Entwicklerheld veröffentlicht. In der Implementierung müsst ihr in einer Methode euren Namen und in einer anderen eure e-Mail-Adresse zurückgeben. Für den Namen könnt ihr euch ein Pseudonym, nein, halt: einen Künstlernamen ausdenken, wenn ihr möchtet. Nur die e-Mail-Adresse muss funktionieren, denn falls wir z.B. die Abschlussveranstaltung online durchführen müssen _oder_ wir ein Problem mit eurem Code haben, sollten wir euch erreichen können. 

## Rechtsausschluss

Es sollte sich von selbst verstehen, aber wir müssen es trotzdem sagen: der Rechtsweg ist ausgeschlossen.

## Links

[1] Challenge auf Entwicklerheld: https://platform.entwicklerheld.de/challenge/treed/?technology=java (*TODO*: Ilja: muss das technology=... sein?)
[2] TreeD Repository auf github: https://github.com/TeamTreeD/public/

*TODO* Steffen: Sollten wir Matt Parker bereits hier referenzieren oder erwähnen?