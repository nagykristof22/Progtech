# Rendszerterv
## 1. A rendszer célja
Az alkalmazás célja, hogy megkönnyítse a kutyák örökbefogadását és örökbeadását, összekötve a potenciális gazdikat az állatmenhelyekkel és a kutyatulajdonosokkal. A rendszer céljai a következők:

Örökbefogadási lehetőségek keresése:

Az örökbefogadni kívánó felhasználók egyszerűen böngészhetnek a rendelkezésre álló kutyák között, szűrők segítségével, mint például fajta, kor, méret, és helyszín alapján.
Örökbeadás:

Azok a kutyatulajdonosok, akik nem tudják tovább gondozni kedvenceiket, feltölthetik a kutyáik profilját, részletes információkkal és fotókkal. Az állatmenhelyek is regisztrálhatják kutyáikat az alkalmazásban.
Profilkezelés:

Minden kutya saját profillal rendelkezik, amely tartalmazza az állapotukat, egészségi információkat, szokásaikat és különleges igényeiket.
## 2. Projektterv

### 2.1 Projektszerepkörök, felelőségek:
  * Üzleti szereplő:
	  -   Megrendelő:
		  -  Troll Ede
     
### 2.2 Projektmunkások és felelőségek:
   * Frontend és backend:
     - Nagy Kristóf
     - Farkas Krisztián
   * Tesztelés:
     - Nagy Kristóf
     - Farkas Krisztián
     
### 2.3 Ütemterv:

|Funkció                  | Feladat                                | Prioritás | Becslés (nap) | Aktuális becslés (nap) | Eltelt idő (nap) | Becsült idő (nap) |
|-------------------------|----------------------------------------|-----------|---------------|------------------------|------------------|---------------------|
|Rendszerterv             |Megírás                                 |         1 |             1 |                      1 |                1 |                   1 |
|Program                  |Prototípus elkészítése                  |         2 |             3 |                      3 |                2 |                   1 |
|Program                  |Tesztelés                               |         3 |             1 |                      1 |                1 |                   1 |

### 2.4 Mérföldkövek:
   *   05.10. Projekt elkezdése
   *   05.11. Alap prototípus elkészítése
   *   05.12. Végleges prototípus elkészítése
   *   05.12. Tesztelés
   *   05.13. Bemutatás és átadás

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők
Az alkalmazás regisztráció vagy bejelentkezés után válik elérhetővé, bárki tud regiszrálni. Minden felhasználó ugyanolyan jogkörrel rendelkezik.

### 3.2 Üzleti folyamatok
Az alkalmazás indulását követően a felhasználónak be kell jelentkeznie a funkciók eléréséhez.
- Általános folyamatok:
     - Regisztrálni az oldalra a megfelelő adatok magadásával.
     - Bejelentkezni az oldalra a regisztráció során megadott megfelelő adatokkal.
     - Örökbeadás vagy fogadás kiválasztása
- örökbeadás
- Fogadás

## 4. Követelmények

### Funkcionális követelmények

| ID | Megnevezés               | Leírás                                                                                                                                                                                   |
|----|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| K1 | Bejelentkezés     | A felhasználónak egyes funkciók elérése előtt bekell jelentkeznie.                                                                                      |
| K2 | Regisztráció             | A felhasználó itt tudja regisztrálni magát.                                                                                                                                              |
| K3 | Mód kiválasztás      | A fehasználó kiválaszthatja, örökbe akar adni vagy fogadni                                                                                                                          |       |
| K4 | Öörkbefogadás | Örökbe lehet fogadni egy kiválasztot állatot és meg tekinteni az edig örökbe fogadott állatainkat. |
| K5 | Öörkbeadás | Örökbe lehet adni egy kutyát. |
 
### Nemfunkcionális követelmények

| ID | Megnevezés                             | Leírás                                                                                                              |
|----|----------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| K6 | Átlátható, könnyen kezelhető felület   | A felületek könnyen használhatóak, átláthatóak legyenek, intuitívak, illetve ne legyenek zsúfoltak.                 |
| K7 | Tervezési minták használata            | Az alkalmazás  tartalmazzon  2 tervezési mintát ez a Stratégia és a Command lesz. |

### Támogatott eszközök

 * Bármely Java alkalmazás futtatásra képes eszköz. Például otthoni számítógép, laptop, tablet. Preferáltabb Windows alapú rendszer.

## 5. Funkcionális terv

### 5.1 Rendszerszereplők
 - Felhasználó
   - Örökbe adhat fogadhat.


### 5.2 Menühierarchiák
- Főoldal (Bejelentkezés és Regisztráció)
- Örökbeadó oldal
- Örökbe fogadó oldal

## 6. Fizikai környezet

### Fejlesztő eszközök
 - IntelliJ IDEA
 - Visual Studio Code
 - XAMPP (MySQL)


## 8. Architekturális terv

### Webszerver

-XAMPP.

### Adatbázis rendszer

- MySQL alapú adatbázis rendszer.

### A program elérése, kezelése
 XAMPP



## 9. Implementációs terv
A projektet két részre oszlik: a frontendre és a backendre. A frontend Java Swing segítségével készül, míg a backend Java keretrendszerben.
## 11. Tesztterv

A tesztelések célja a rendszer és komponensei funkcionalitásának teljes vizsgálata,

A tesztelés során a szoftver megfelelő működését vizsgáljuk. Amennyiben az elvártnak megfelelő eredményt kapunk, a teszt eset sikeresnek tekinthető, ellenkező esetben a hibát megpróbáljuk elhárítani, ha a teszt nem direkt nem sikerül.

### Tesztesetek

#### Tesztelés módja: Unit Teszt

 | Teszteset      | Elvárt eredmény                                                                                            | 
 |----------------|------------------------------------------------------------------------------------------------------------| 
 | Regisztráció   | A felhasználó az adatok megadásával sikeresen regisztrálni tud.                                            |
 | Bejelentkezés  | A felhasználó az adatok megadásával sikeresen be tud jelentkezni.                                          |
 | Örökbefogadás | Elérhető statuszról változzon örökbefogadott státuszra |
 | Örökbeadás | Kerüljön bele az elérhető listába                         |


## 12. Karbantartási terv
Fontos ellenőrizni:
*	Az alkalmazás megfelelően kezeli a kritikus információkat, azok nem elérhetők a megfelelő jogkör és felhasználói adatok nélkül. Ilyenek például a bejelentkezési adatok, és a felhasználók személyes adatai adatai.

