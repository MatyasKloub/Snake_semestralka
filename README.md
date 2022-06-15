# Snake_semestralka
Semestrální práce předmětu ALG2 - Snake
Matyáš Kloub

Zadání
Aplikace musí obsahovat následující:
-Menu s možností restartu, vypnutí aplikace
-Výpis nějakých výsledků na konzoli - použití Stringbuilder
-Načítání dat alespoň z 2 souborů
-Zápis dat do souboru
-Možnost práce s textovými a binárními soubory (alespoň někde)
-Využití otevřených dat
-Adresář data, popř. třída DataStore
-Použití Interface
-Použití java.time API pro práci s časem
-Použití enum
-Použití kontejnerové třídy jazyka java
-Alespoň 2 možnosti třídění (comparable)
-Regex
-Ošetření vstupů
-Použití externí knihovny
-Javadoc



Ukázka vstupu a výstupu: 
- Aplikace je plně grafická.
- Pro hraní hry se využívají šipky
- Pro pohyb v menu myš a klávesnice pro zadání jména


![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic1.png)


Návrh a popis řešení
Využití Graphics komponentu JPanel
Využití tohoto komponentu k úplnému chodu celé hry, je využit pro vykreslování hada, gridu (který je vykreslován pouze pro lepší hratelnost). 
Využití Stringbuilder
V celé aplikaci je zásadně využíván pouze Stringbuilder během práce se všemi textovými proudy.

![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic2.png)
Obr. 2 - Příklad využití StringBuilder

Zápisy dat do souboru
aplikace zapisuje do dvou souborů ve složce “data”, pokud složka nebo soubory neexistují, aplikace je vytvoří.
1. soubor ve složce “data”, - “colors.bin”
- jedná se o binární soubor
- je v něm uložena barva (RGB) - tedy 3 čísla binárně
  - tato barva reprezentuje barvu hada, na začátku je vždy zelená (pokud by soubor neexistoval), a po jakékoliv změně od uživatele v menu se okamžitě nová barva uloží a při dalším spuštění se bude stále ukazovat dokud nebude změněna
- 2. soubor ve složce “data”, - “game_score.csv”
    - jedná se o textový CSV soubor
    - v tomto souboru jsou uložena veškerá skóre všech hráčů v následujícím formátu: skore#jmeno;skore#jmeno… atd. (skore je číslo), jméno nemůže obsahovat cizí znaky, stejně jako skóre nemůže obsahovat nic jiného než číslo
- Zápisy do souborů dělají funkce v třídách, které se nachází ve složce “File”
    - FileAction.java
    - FileBinaryAction.java
Využití Otevřených dat
- Využívám otevřených dat LocalDate, pro zjištění času vždy při zapnutí aplikace.
- Adresář data, popř. třída Datastore
- Adresář data je vždy vytvořen ve tříde FileAction.java, spolu s daty které do složky přijdou.

Použití Interface
- používám vlastní interface “IMove”, jsou v něm abstraktně definované dvě metody které využívá had, “Move” a “CheckCollisions”
- dále jsou použity různé interface jako např. Comparable či ActionListener
- ActionListener interface je využit pro kontrolu příchozích akcí z aktivního Frame, kontroluje zmáčknutí určitých tlačítek a na základě toho popř. mění směr kterým se had pohybuje
Použití java.time pro práci s časem
- v aplikaci je použit Timer pro počítání “tiků” mezi tím než se had znovu pohne
- Je spuštěn na začátku s parametry “DELAY” (což je má proměnná, která obsahuje mnou určený “tik”) a “this”, což je Frame samotný).
	
  ![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic3.png)
		Obr. 3 - Spuštění Timeru pro tik
Použití enum
enum je použit pro definici hodnot “směrů” kam se had může pohnout ve tříde faceDir.java 

![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic4.png)
	        Obr. 4 - Definice enum
          
Použití kontejnerové třídy jazyka Java + možnosti třídění
- Při načtení skóre v třídě FileAction.java je využit ArrayList pro uchování bodů + jmén hráčů
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic5.png)
	Obr. 5 Definice ArrayList pro skóre 

- Dále je využit v metodách pro sort listů “sortByName()” a “sortByPoints()”
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic6.png)	
		Obr. 6 využití listů při třídění v metodách
    
Použití regulárních výrazů (regex)
- Regulární výrazy jsem využil při kontrole jména uživatele. 
- Kontrola se nachází v třídě InputControl
- Jedná se o kontrolu zda jméno obsahuje pouze čísla nebo písmena, vrací false pokud obsahuje něco jiného
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic7.png)
	Obr. 7 - Využití regulárního výrazu

- Pokud obsahuje nějaký špatný znak, v menu se objeví červeně napsaný error a hra se nespustí dokud uživatel nezadá validní jméno
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/pic8.png)
		Obr. 8 - Ošetření vstupu

Využití externí knihovny
- jak je možné od začátku dokumentace vidět, využívám Swing jako externí knihovnu, od menu, po hru.

Generování javadoc
- všechny metody v projektu včetně objektů mají javadoc popisek, lze bez jakýchkoliv errorů či varování javadoc vygenerovat. 


Vývojový diagram





![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/vyvoj.png)

Class diagram




![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/class_diag.png)

- Testování

![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test1.png)
- Test č.1 Základní jméno s použitím znaků
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test2.png)
- Test č. 2 Použití pouze čísel pro jméno
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test3.png)
- Test č. 3 - Použití čísel i písmen pro jméno
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test4.png)
- Test č. 4 - použití speciálních znaků 
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test5.png)
- Test č. 5 složka “data” se úspěšně vytvořila + v ní rovnou soubor
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test6.png)
- Test č. 6 Pouze složka existovala (prázdná), oba soubory se najednou vytvořily
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test7.png)
- Test č.7 Po spuštění se automaticky vytvořil soubor colors.bin s defaultní barvou
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test8.png)
- Test č.8 Hadův ocas má správnou barvu (hlava se nemění)
![alt text](https://github.com/MatyasKloub/Snake_semestralka/blob/main/doc/test9.png)
- Test č.9 barva zůstala stejná po znovu zapnutí hry

