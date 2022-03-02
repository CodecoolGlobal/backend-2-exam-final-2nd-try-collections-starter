# Jolr'a Tours turistairoda

## Bevezetés

Turista irodának készítesz programot, szükség lenne a turisták különböző szempontok szerinti csoportosítására az ajánlat
készítéshez.

## Feladatleírás

Készíts egy, a teszteknek megfelelő `Tourist` osztályt, amiben tárolni tudod a következő adatokat:
a turista nevét, születési dátumát és a hobbiait (String halmazban tárolva). Azt is meg tudja mondani, hogy egy adott
dátumkor épp hány éves: `int getAge(LocalDate now)`.

Készíts el egy, a teszteknek megfelelő `TouristClassifier` osztályt, mely:

- a turisták listáját konstruktor paraméterben kapja meg, ha ez null, dobjon `RuntimeException`-t
- képes visszaadni a fiatal, adott napon 18 évesnél fiatalabb turistákat
    - `List<Tourist> findYoungTourists(LocalDate now)`
- képes visszaadni adott napon, adott évnél idősebb turistákat
    - `List<Tourist> findTouristsOlderThan(int year, LocalDate now)`
- képes visszaadni az elkövetkezendő évben (12 hónapban) megtartandó szülinapok dátumjaival, és hogy azon a dátumon épp
  hány szülinapot kell ünnepelni - `findBirthDaysInNextYear`
- képes visszaadni azokat a turistákat, akiknek legalább 2 közös hobbijuk van a paraméterben megadottakkal. Ha nem
  adtunk meg legalább 2 hobbit, dobjunk IllegalArgumentException-t!
    - `List<Tourist> findTouristsWithHobbies(Set<String> hobbies)`
- képes visszaadni egy, a turisták neveit tartalmazó, születési hónap alapján csoportokba rakott
  map-ot `Map<Month, List<String>> getNamesPerMonth()`

A munka során a kísérletezéshez tetszőlegesen létrehozhatsz `main` metódust, illetve akár készíthetsz másik
osztályt `main` metódussal.

A teszteket nem szabad módosítani!

