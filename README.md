# Engine pre šachovnicové hry
Návrhové vzory semestrálna práca - engine pre šachovnicové hry.
Postup pri vytvorení novej šachovnicovej hry
1. Vytvorenie figúriek, ktoré adaptujú triedu Piece (ak si daná figúrka musí udržiavať nejaké dodatočné informácie alebo neexistuje ešte adaptácia, ktorá má požadovanú grafickú reprezentáciu)
2. Vytvorenie sluhov ktorí nastavujú možné ťahy pre figúrky použité v hre (ak takí sluhovia ešte neexistujú)
3. Vytvorenie potomka triedy Game
    - Je nutné prepísať metódu placePiecesOnBoard() – pre počiatočné rozostavenie
    figúriek na šachovnicu
    - Prepísať metódu checkGameEndingConditions(), pre kontrolu konca hry
    - Ak je mechanizmus hry iný ako po posunutí figúrky len zrušiť výber vybranej
    figúrky aaktualizácia možných ťahov, je nutné prepísať aj metódu
    afterPieceMoved()
    - Ak je výber figúrky podmienený ešte nejakým pravidlom navyše je nutné
    prepísať metódu checkSelectionCondition(Piece piece)