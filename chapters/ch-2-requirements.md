# Requisiti

Questo capitolo sarà dedicato alla descrizione dei requisiti che il progetto dovrà soddisfare. Le sezioni sottostanti
definiranno le varie tipologie di requisiti quanto più in dettaglio.

## Requisiti di business

L'applicazione dovrà disporre delle seguenti caratteristiche:

- Visualizzazione di una GUI che permetta all'utente di giocare immettendo le proprie decisioni nel sistema;
- architettura che consenta agevolmente di spostarsi tra i vari nodi della storia in base alle decisioni prese;
- interfaccia che permetta all'utente di scegliere la storia da intraprendere tra quelle disponibili e gestire i
  salvataggi;
- sistema che permetta al giocatore di allocare dei punti al fine di personalizzare statistiche del proprio personaggio;
- sistema che consenta al giocatore di consultare le proprie statistiche in itinere, esse potranno infatti subire
  variazioni dovute all'equipaggiamento di oggetti o all'accadimento di eventi;
- battle system che proporrà scontri 1 vs 1 contro nemici controllati dal sistema.

## Requisiti utente
I requisiti utente esprimono i bisogni degli utenti e descrivono quali sono le azioni che l’utente deve poter effettuare interagendo con il sistema.  

Al fine di esprimere in maniera concisa tali requisiti sono state impiegate le User stories e i Casi d'uso.

### User stories
Le user stories sono delle specifiche informali, scritte dal punto di vista dell'utente finale, che descrivono ad alto livello le funzionalità che il sistema dovrà possedere.

**WHO:**  
In quanto giocatore

1.  - **WHAT:**  
    vorrei poter scegliere tra più storie da giocare
    - **WHY:**  
    per aumentare la varietà
<br/><br/>
2.  - **WHAT:**  
    vorrei poter aggiungere nuove storie
    - **WHY:**  
    per espandere e protrarre l'esperienza di gioco
<br/><br/>
3.  - **WHAT**  
    vorrei poter navigare una storia compiendo scelte
    - **WHY**  
    per immedesimarmi nella psicologia del personaggio da me creato
<br/><br/>
4.  - **WHAT**  
    vorrei poter selezionare le caratteristiche del mio personaggio
    - **WHY**  
    per scegliere delle caratteristiche che avvantaggino una certo stile di gioco
<br/><br/>
5.  - **WHAT**  
    vorrei che le caratteristiche del mio personaggio influenzino la storia
    - **WHY**  
    per poter giocare nuovamente la stessa storia prendendo scelte che prima non erano disponibili
<br/><br/>
6.  - **WHAT**  
    vorrei poter consultare un riepilogo della storia giocata fino al mio attuale progresso
    - **WHY**  
    per poter rivisitare tutti i dettagli della mia avventura corrente
<br/><br/>
7.  - **WHAT**  
    vorrei poter salvare i miei progressi in una storia
    - **WHY**  
    per poter riprendere a giocare dallo stesso punto in futuro 
<br/><br/>
8.  - **WHAT**  
    vorrei poter sostenere delle battaglie contro dei nemici
    - **WHY**  
    per potermi mettere alla prova
<br/><br/>
8.  - **WHAT**  
    vorrei poter gestire degli oggetti
    - **WHY**  
    per sbloccare nuove scelte, personalizzare il mio personaggio e ricevere aiuto in situazioni critiche dovute a combattimenti
<br/><br/>

 
### Casi d'uso
Il diagramma UML dei casi d'uso è visto dal "lato gaming", cioè vuole esprimere ciò che l'utente finale può fare interagendo con il sistema allo scopo di giocare.

<div align="center">
  <img src="https://images2.imgbox.com/ad/48/cxZ6HHjH_o.png" alt="Diagramma dei casi d'uso - lato gaming">
  <p align="center">Diagramma dei casi d'uso - lato gaming</p>
</div>

Un aspetto di grande importanza nella progettazione del sistema è sicuramente la possibilità di aggiungere potenzialmente un numero illimitato di storie; il sistema riuscirà ad interpretare queste ultime e farà navigare l'utente all'interno di esse. È dunque fondamentale che l'utente possa aggiungere nuove storie (reperendole da fonti esterne) e navigare tra esse scegliendo quale giocare.

## Requisiti funzionali

## Requisiti non funzionali
- **Cross-platform**  
Il sistema deve funzionare correttamente sui sistemi operativi Windows, Linux e MacOS.
- **Interfaccia utente intuitiva**  
L'utente deve poter navigare all'interno del sistema attraverso un'interfaccia utente curata e di facile utilizzo.
- **Shortcut**  
Un utente esperto può navigare all'interno del sistema utilizzando shortcut da tastiera.
## Requisiti di implementazione
I requisiti di implementazione vincolano l'intera fase di realizzazione del sistema, ad esempio richiedendo l'uso di uno specifico linguaggio di programmazione e/o di uno specifico tool software.  

I requisiti di implementazione sono i seguenti, divisi per categoria: 
1. __Build Tool__: Il build tool utilizzato è sbt;
2. __Linguaggio utilizzato__: Scala Quest è stato sviluppato utilizzando Scala 2;
3. __Suite di Testing__: Il testing è avvenuto mediante la libreria ScalaTest, al fine di poter mantenere e aggiornare in modo
semplice, esplicativo e controllato il sistema e le sue funzionalità;
4. __Librerie__: Eventuali librerie aggiuntive sono importabile tramite l'aggiunta di dipendenze all'interno di sbt. 

---