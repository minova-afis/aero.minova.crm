# aero.minova.crm
Wiki und Ticketsystem für unser CRM

## Kommentare und Texte

Alle Kommentare werden in Markdown geschrieben.
Für Markdown gibt es eine Java-Bibliothek, die sich auch einfach in Eclipse E4 RCP einbinden lassen.

Alle Kommentare werden in einer Tabelle gespeichert. 
Dadurch lassen sich alle Texte leichter durchsuchen.

## Überlegungen zur Integration Trac

Wir können die Daten von Trac über XML-RPC abrufen.
Das Format ist leider nicht kompatible mit Markdown.

Folgende Änderungen können wir vor dem Konvertieren des Markdown übernehmen.

### Überschriften

Im Wiki werden Überschriften mit `=` Zeichen erstellt.
Sie können auch noch am Ende der Zeile auftauchen.

```
=== Überschrift H3 ===
Text danach
```

Dies muss in `#` umgesetzt werden. 
Die Zeichen am Ende der Zeile dürfen nicht übernommen werden.

```
### Überschrift H3
Text danach
```

Dies kann in Java mit String#replaceAll() erreicht werden.

```
String s = "=== Überschrift H3 ==="; // Beispiel
s = s.replaceAll("\n== (.*?)( ==)?\n", "\n## $1\n");
```

### Bilder

Auf Wiki-Seiten und in Tickets kann man auf Bilder zurückgreifen, die man als Attachment zur Seite oder zum Ticket hochladen kann.

Wenn das Bild `picture.png` zur gleichen Seite hochgeladen wurde, kann man er mit dem Makro `[[Image(picture.png)]]` auf der Seite inline darstellen lassen.

In Markdown sollte man es als HTML übersetzen, dann gehen noch weitere Makro-Optionen.
Die Bilder erreicht man im Trac unter der Adresse 

- `/attachment/ticket/<<nummer>>` für Tickets
- `/attachment/wiki/<<seitenname>>` für Wiki-Seiten

```
<img src="picture.png" />
```