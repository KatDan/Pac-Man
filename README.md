# Pac-Man
zápočtový program z Javy, zimný semester 2019/2020
<br>

Program je zjednodušená varianta hry Pac-Man, kde je cieľom hráča zozbierať všetky "jabĺčka" v bludisku a nenechať sa pritom zjesť bubákmi.

Hráč má na začiatku hry 3 životy. Keď ho nejaký bubák chytí, stráca jeden život a všetky doposiaľ nahraté body a začína odznova.



## Používateľské rozhranie
- hra sa spúšťa klávesou **"SPACE"**

- po spustení hry sa hráč pohybuje šípkami na klávesnici
- hru je možné pauznúť stlačením klávesy **"P"**

- program sa po stratení všetkých životov alebo po zozbieraní všetkých jabĺčok vypne klávesou **"SPACE"**

Nahraté skóre sa počíta v ľavom hornom rohu okna a stav svojich zvyšných životov je možné sledovať v pravom hornom rohu.

## Programátorská dokumentácia
Program je napísaný v jazyku _Java 11.0.4_. Okrem základnej knižnice sú použité aj knižnice _javax.swing_, _java.awt_ a _javax.imageio_.
Obrázky postáv a mapa sú uložené vo formáte _".png"_ v priečinku "resources", ktoré je možné v rovnomenných súboroch formátu _".xcf"_ v prípade potreby meniť.

### Hlavné triedy:
- **Game** - v tejto triede sa inicializujú všetky objekty potrebné na chod programu, spravuje sa _frame_ a taktiež labely. Najpodstatnejšie komponenty triedy sú:
  - **run()** - stará sa o animáciu počas behu programu, dejú sa v nej metódy **tick()** a **render()**
  - **tick()** - urobí jeden "krok" behu hry, teda pohyb hráča, bubákov a update nahratého skóre
  - **render()** - po urobení kroku tento krok vykreslí na frame
  - **new_game()** - nastaví počiatočnú pozíciu hry
  - **keyPressed(KeyEvent e)** - umožňuje hráčovi používať klávesnicu

- **Graph** - mapa hry je reprezentovaná štvorčekovou sieťou, kde je jeden štvorček jeden pixel mapy, teda jeden štvorček v hre. Táto trieda pomocou pomocných tried **Vertex** a **Node** umožňuje spravovať voľné políčka tejto štvorčekovej siete, aby umožnila bubákom hľadanie najkratšej cesty k hráčovi. Najdôležitejšie komponenty:
  - Vertex **bfs(Vertex start, Vertex end)** - nájde najkratšiu cestu z vrcholu **start** do vrcholu **end** a uloží ju do Node() **result**.  Výstupom je prvé políčko pri **start**e na ceste k **end**u

- **Player** - spravuje všetky akcie, ktoré Pac-Man robí, najdôležitejšie komponenty sú:
  - boolean **om_nom_nom_switch** - umožňuje Pac-Manovi otvárať a zatvárať ústa
  - enum **Direction** - podľa smeru pohybu vykreslí správnu hlavu
  - **can_move(int xx, int yy)** - zistí, či na pozícii [xx,yy] nekoliduje Pac-Man s nejakým blokom
  - **find_position()** - metóda podľa pozície hráča určí jeho pozíciu na štvorčekovej sieti spomenutej pri triede **Graph**

- **Ghost** - spravuje pohyb a pozíciu bubáka. Dôležité sú:
  - Vertex **checkpoint** - vrchol, do ktorého bubák smeruje, je výstupom metódy **Graph.bfs()**
  - **move()** - zisťuje, či sa bubák nedotkol hráča
               - po dosiahnutí checkpointu hľadá bubákovi nový. Aby mali bubáky rozmanitejšie trajektórie, koncová pozícia je náhodne vyberaná z vrcholu, kde sa nachádza Pac-Man a jeho susedných vrcholov
               - ak bubák v checkpointe ešte nie je, posunie ho správnym smerom
  - **find_position(int xx, int yy)** - podobne ako v triede **Player** určí, v ktorom vrchole sa bubák podľa jeho aktuálnych súradníc nachádza 

- **Level** - stará sa o chod bubákov, životy hráča a nazbierané jabĺčka. Dôležité komponenty:
  - **Level()** z obrázku mapy vyčíta počiatočné pozície bubákov a hráča a voľné políčka. Vytvára graf voľných políčok.
  - **find_neighbors(Vertex w)** - nájde skutočné susedné políčka vrcholu **w**, teda políčka nad, pod alebo vedľa aktuálneho políčka
  - **render()** - renderuje pohyb bubákov, jabĺčka, bloky a životy.

Prajeme príjemnú zábavu!



