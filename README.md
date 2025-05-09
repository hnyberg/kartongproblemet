# kartongproblemet

## Uppgiften

Skapa en applikation där man skickar in en samling artiklar och få svar på vilken kartong som skall användas för att packa artiklarna i.

### Storlekar kartonger:

1. 4 x 5
2. 8 x 12
3. 12 x 20

### Storlekar artiklar:

1. 1 x 1 
2. 1 x 2
3. 1 x 4
4. 1 x 6
5. 1 x 8
6. 1 x 9
7. 1 x 12
8. 1 x 5
9. 1 x 9

### Exempel:

- 6 st artikel 7, 2 st artikel 4, 4 st artikel 1 => kartong nr 2
- 3 st artikel 3, 1 st artikel 1, 1 st artikel 2 => kartong nr 1
- 1 st artikel 5, 3 st artikel 4 => kartong nr 2
- 12 st artikel 7, 100 st artikel 1 => "Upphämtning krävs"
- 4 st artikel 8 => kartong nr 1

## Regler för funktion:
- Vi strävar alltid efter att använda minsta möjliga kartong
- Man behöver inte vrida eller snurra artiklarna
- Endast en kartong får returneras som svar
- Om artiklarna inte får plats i en kartong så får kunden hämta på plats - svara då "Upphämtning krävs"

## Krav för app:
- Lösningen skall ha enhetstester
- Lösningen skall vara gjord i SpringBoot

## Instruktioner för användning
Kör jar-filen med 1 Sträng-argument i följande form (exempel): "6 st artikel 7, 2 st artikel 4, 4 st artikel 1".
Programmet skriver då ut vilken kartong som bör användas, i detta fall "kartong nr 2".
Om ingen lämplig kartong finnes, så skrivs "Upphämtning krävs" ut.
Felaktigt format på input kommer ge svar om detta, och programmer kommer ej köras vidare.