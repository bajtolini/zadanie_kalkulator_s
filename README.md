# zadanie_kalkulator_s

## General
- Program do wyliczania miesięcznej stawki netto na podstawie dniówki brutto, stałych opłat i podatków podanych przez użytkownika
- Możliwość przewalutowania na złotówki na podstawie kursów z zewnętrznego api

## Backend
- Kursy są pobierane z zewnętrznego api
- Aby ułatwić tworzenie nowych krajów został zaimplementowany schemat fabryki

## Frontend
- Sidebar został stworzony gdyby zaistniała potrzeba rozwinięcia strony o podstrony
- Dodano walidacje formularza przed wysłaniem
- Wszystkie komunikaty otrzymywane z backendu są wyświetlane za pomocą Toast-ów

## Dodatkowe dane
- W testach występuje błąd spowodowany połączeniem Mock-ów, JUnit4 i JUnit5

```
Initialization Error
org.mockito.exceptions.base.MockitoException:

No tests found in CountryFactoryTest
Is the method annotated with @Test?
Is the method public?
```
