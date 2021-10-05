# Product Backlog
Il contenuto di queste tabelle estrapola le informazioni principali del lavoro effettuato su Trello, nel quale sono stati tracciati tutti i task assegnati ed eseguiti in ogni sprint. Le tempistiche dei task sono state riportate in modo fedele a quanto annotato su Trello.  
La board utilizzata può essere trovata al seguente link: https://trello.com/b/5M0cOIzN .  

| Priority | Item                                               | Details                                                                                                                                     | Initial Size Estimates | Sprint 1 | Sprint 2 | Sprint 3 | Sprint 4 | Sprint 5 | Sprint 6 | Sprint 7 |
|----------|----------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|------------------------|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|:--------:|
|     1    | Implementazione elementi principali del Model      | Implementazione degli elementi basi del model, che costituiscono le strutture dati principali per il funzionamento del gioco                |           30           | 15       | 0        | 0        | 0        | 0        | 0        | -        |
|     2    | Implementazione View                               | Implementazione della view, inizialmente a riga di comando e poi con effettive GUI                                                          |           50           | 45       | 40       | 32       | 21       | 10       | 0        | -        |
|     3    | Implementazione logica per la navigazione dei nodi | Implementazione della logica per navigari i nodi, inizialemente senza condizioni che vincolano la visualizzazione delle scelte al giocatore |           20           | 10       | 0        | 0        | 0        | 0        | 0        | -        |
|     4    | Gestione serializzazione/deserializzazione storia  | Gestione delle serializzazione e deserializzazione della storia, con salvataggio e caricamento della storia da file                         |            9           | 9        | 4        | 0        | 0        | 0        | 0        | -        |
|     5    | Implementazione elementi aggiuntivi del Model      | Implementazione di ulteriori elementi del model, tra cui gli oggetti e gli eventi, che arricchiscono le possibili funzionalit√† nel gioco   |           20           | 20       | 20       | 0        | 0        | 0        | 0        | -        |
|     6    | Implementazione logiche con Model aggiuntivo       | Modifica dei controlli al fine di interagire con i nuovi elementi inseriti nel Model (combattimenti, inventario, stats)                     |           25           | 25       | 25       | 25       | 0        | 0        | 0        | -        |
|     7    | Implementazione Editor                             | Implementazione di un editor che permetta di creare una storia, conforme a tutte le regole del gioco                                        |           30           | 30       | 30       | 30       | 17       | 0        | 0        | -        |
|     8    | Creazione di esplorazione nodi tramite Prolog      | Permettere la navigazione di tutte le storie possibili sfruttando il Prolog                                                                 |           11           | 11       | 11       | 11       | 11       | 0        | 0        | -        |
|     9    | Inserimento elementi aggiuntivi                    | Aggiunta di musica                                                                                                                          |            3           | 3        | 3        | 3        | 3        | 0        | 0        | -        |
|    10    | Refactor generale                                  | Riguardare insieme le classi e refactorare se necessario                                                                                    |           15           | 15       | 15       | 15       | 15       | 15       | 0        | -        |
  
## Sprint 1
1/08/2021 - 11/08/2021

| Product Backlog Item                               | Sprint Task                                | Volunteer      | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|----------------------------------------------------|--------------------------------------------|----------------|------------------------|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:-----:|:------:|
| Implementazione elementi principali del Model      | Creazione Character                        | Sanchi         | 3                      | 3     | 2     | 2     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione StoryNode                        | Tronetti/Talmi | 4                      | 4     | 4     | 2     | 2     | 1     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione Pathway                          | Tronetti/Talmi | 3                      | 3     | 3     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione StoryModel                       | Tronetti       | 4                      | 3     | 3     | 3     | 3     | 3     | 2     | 2     | 1     | 0     | 0      |
| Implementazione View                               | Creazione StoryView                        | Sanchi         | 5                      | 5     | 4     | 4     | 2     | 1     | 0     | 0     | 0     | 0     | 0      |
| Implementazione logica per la navigazione dei nodi | Creazione MasterController e SubController | Talmi/Filaseta | 10                     | 10    | 5     | 3     | 1     | 1     | 0     | 0     | 0     | 0     | 0      |

## Sprint 2
11/08/2021 - 21/08/2021

| Product Backlog Item                               | Sprint Task                             | Volunteer | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|----------------------------------------------------|-----------------------------------------|-----------|------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|--------|
| Implementazione elementi principali del Model      | Creazione PropertiesContainer           | Sanchi    | 2                      | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione classe Health                 | Sanchi    | 2                      | 2     | 2     | 2     | 2     | 2     | 2     | 2     | 2     | 1     | 0      |
|                                                    | Creazione Stats                         | Tronetti  | 3                      | 3     | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione StatsModifier                 | Tronetti  | 3                      | 3     | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione History Model                 | Talmi     | 3                      | 3     | 2     | 2     | 1     | 1     | 1     | 1     | 1     | 1     | 0      |
| Implementazione View                               | Creazione StoryView con Swing           | Sanchi    | 10                     | 10    | 9     | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 0      |
|                                                    | Creazione schermata statistiche         | Talmi     | 5                      | 5     | 5     | 5     | 5     | 5     | 4     | 2     | 1     | 0     | 0      |
|                                                    | Creazione History View                  | Talmi     | 5                      | 5     | 5     | 5     | 5     | 4     | 4     | 3     | 2     | 1     | 0      |
| Implementazione logica per la navigazione dei nodi | Creazione PrerequisiteStrategy          | Talmi     | 2                      | 2     | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                    | Creazione History Controller            | Talmi     | 5                      | 5     | 5     | 4     | 2     | 2     | 1     | 1     | 1     | 1     | 0      |
| Gestione serializzazione/deserializzazione storia  | Serializazione/Deserializzazione storia | Filaseta  | 9                      | 9     | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 0     | 0      |
|                                                    | Caricamento storia da file              | Filaseta  | 9                      | 9     | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 1     | 0      |

## Sprint 3
21/08/2021 - 31/08/2021

| Product Backlog Item                              | Sprint Task                                         | Volunteer         | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|---------------------------------------------------|-----------------------------------------------------|-------------------|------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|--------|
| Implementazione View                              | Refactor GUI                                        | Talmi/Sanchi      | 9                      | 9     | 8     | 6     | 6     | 5     | 4     | 2     | 2     | 1     | 0      |
|                                                   | Creazione View salvataggi                           | Talmi             | 3                      | 3     | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                   | Creazione Menu principale                           | Sanchi            | 4                      | 4     | 3     | 3     | 2     | 1     | 1     | 0     | 0     | 0     | 0      |
| Gestione serializzazione/deserializzazione storia | Salvataggio effettivo della storia                  | Talmi             | 5                      | 5     | 5     | 5     | 5     | 3     | 2     | 1     | 0     | 0     | 0      |
| Implementazione elementi aggiuntivi del Model     | Creazione Inventario                                | Tronetti/Filaseta | 3                      | 3     | 2     | 1     | 1     | 1     | 0     | 0     | 0     | 0     | 0      |
|                                                   | Creazione Enemy                                     | Sanchi            | 1                      | 1     | 1     | 1     | 1     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                   | Creazione Event                                     | Talmi             | 3                      | 3     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                   | Creazione EquipItemType                             | Tronetti          | 1                      | 1     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                                   | Creazione Item (EquipItem, ConsumableItem, KeyItem) | Tronetti/Filaseta | 6                      | 6     | 6     | 5     | 4     | 2     | 2     | 1     | 1     | 0     | 0      |

## Sprint 4
31/08/2021 - 10/09/2021

| Product Backlog Item                         | Sprint Task                                                                        | Volunteer         | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|----------------------------------------------|------------------------------------------------------------------------------------|-------------------|------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|--------|
| Implementazione View                         | Aggiunta storie                                                                    | Filaseta/Sanchi   | 3                      | 3     | 3     | 3     | 2     | 1     | 1     | 0     | 0     | 0     | 0      |
|                                              | Eliminazione storie                                                                | Sanchi            | 4                      | 4     | 4     | 4     | 3     | 2     | 2     | 1     | 1     | 0     | 0      |
|                                              | Creazione schermata inventario                                                     | Filaseta/Sanchi   | 7                      | 7     | 4     | 3     | 1     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                              | Creazione schermata combattimenti                                                  | Tronetti          | 5                      | 5     | 5     | 5     | 5     | 5     | 2     | 2     | 1     | 0     | 0      |
| Implementazione logiche con Model aggiuntivo | Creazione logica eventi                                                            | Sanchi            | 4                      | 4     | 4     | 3     | 2     | 1     | 1     | 0     | 0     | 0     | 0      |
|                                              | Creazione logica dell'inventario                                                   | Filaseta/Sanchi   | 4                      | 4     | 3     | 2     | 1     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                              | Creazione logica combattimenti                                                     | Tronetti          | 8                      | 8     | 7     | 6     | 5     | 4     | 2     | 1     | 0     | 0     | 0      |
|                                              | Creazione integrazione tra inventario e battaglia                                  | Tronetti/Filaseta | 3                      | 3     | 3     | 3     | 3     | 3     | 3     | 3     | 2     | 1     | 0      |
| Implementazione Editor                       | Creazione ed eliminazione di Nodi e Pathway nell'editor                            | Talmi             | 10                     | 9     | 8     | 5     | 4     | 4     | 3     | 2     | 1     | 1     | 0      |
|                                              | Controlli per assicurarsi che nodi e pathway non vadano contro le regole del model | Talmi             | 10                     | 9     | 8     | 5     | 4     | 4     | 3     | 2     | 1     | 1     | 0      |
|                                              | Implementazione Form Customizzati                                                  | Filaseta/Tronetti | 5                      | 5     | 4     | 2     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |

## Sprint 5
10/09/2021 - 20/09/2021

| Product Backlog Item                          | Sprint Task                                                                               | Volunteer         | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|-----------------------------------------------|-------------------------------------------------------------------------------------------|-------------------|------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|--------|
| Implementazione View                          | Aggiunta nome Player                                                                      | Sanchi            | 1                      | 1     | 1     | 1     | 1     | 0     | 0     | 0     | 0     | 0     | 0      |
| Implementazione Editor                        | Aggiungere eventi a un nodo                                                               | Talmi             | 10                     | 10    | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 1     | 0      |
|                                               | Aggiungere enemy                                                                          | Talmi             | 10                     | 10    | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 1     | 0      |
|                                               | Aggiungere condizioni                                                                     | Talmi             | 10                     | 10    | 8     | 7     | 6     | 5     | 4     | 3     | 2     | 1     | 0      |
| Creazione di esplorazione nodi tramite Prolog | Aggiunta navigazione di tutte le storie possibili a partire da un certo nodo              | Tronetti/Filaseta | 10                     | 10    | 10    | 9     | 8     | 7     | 4     | 1     | 0     | 0     | 0      |
|                                               | Aggiunta di tutte le storie possibili (dal nodo iniziale a tutti i possibili nodi finali) | Tronetti/Filaseta | 10                     | 10    | 6     | 4     | 3     | 2     | 1     | 0     | 0     | 0     | 0      |
|                                               | Inserimento motore Prolog                                                                 | Filaseta          | 5                      | 5     | 3     | 1     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                               | Controllo che esista un pathway da tra due nodi della storia                              | Tronetti/Filaseta | 10                     | 10    | 10    | 8     | 7     | 7     | 5     | 3     | 2     | 1     | 0      |
|                                               | Estrarre da uno storynode la sua descrizione                                              | Tronetti/Filaseta | 5                      | 5     | 3     | 0     | 0     | 0     | 0     | 0     | 0     | 0     | 0      |
|                                               | Estrarre da uno storynode la descrizione di un suo pathway                                | Tronetti/Filaseta | 6                      | 6     | 5     | 3     | 2     | 1     | 1     | 0     | 0     | 0     | 0      |
| Inserimento elementi aggiuntivi               | Aggiunta musica                                                                           | Sanchi            | 3                      | 3     | 3     | 2     | 1     | 1     | 1     | 0     | 0     | 0     | 0      |

## Sprint 6
20/09/2021 - 30/09/2021

| Product Backlog Item            | Sprint Task                                                        | Volunteer                      | Initial Size Estimates | Day 1 | Day 2 | Day 3 | Day 4 | Day 5 | Day 6 | Day 7 | Day 8 | Day 9 | Day 10 |
|---------------------------------|--------------------------------------------------------------------|--------------------------------|------------------------|-------|-------|-------|-------|-------|-------|-------|-------|-------|--------|
| Inserimento elementi aggiuntivi | Utilizzare musiche diverse a seconda della view in cui ci si trova | Sanchi/Filaseta                | 4                      | 4     | 3     | 2     | 2     | 1     | 1     | 0     | 0     | 0     | 0      |
| Refactor generale               | Refactor model                                                     | Talmi/Sanchi/Filaseta/Tronetti | 10                     | 10    | 9     | 8     | 7     | 6     | 5     | 4     | 3     | 1     | 0      |
|                                 | Refactor tests                                                     | Talmi/Sanchi/Filaseta/Tronetti | 10                     | 10    | 9     | 8     | 7     | 6     | 5     | 4     | 3     | 1     | 0      |