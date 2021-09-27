# Restrospettiva
<!-- NOTA: (descrizione finale dettagliata dell'andamento dello sviluppo, del backlog, delle iterazioni; commenti finali) -->
## Descrizione degli sprint
In questo capitolo si tratterà della descrizione dettagliata degli Sprint effettuati, con una particolare attenzione agli obiettivi posti per ogni sprint e agli effettivi risultati ottenuti.  
La durata di uno Sprint è stata fissata a 10 giorni, al fine di permettere di produrre risultati significativi che hanno costituto la base per l'inizio dello Sprint successivo.

### Sprint 1 (1/08/2021 - 11/08/2021)
#### Obiettivo
L'obiettivo di questo Sprint è stato quello di svolgere una prima analisi del problema, che ha portato conseguentemente ad una fase di progettazione.  
Il focus principale era quello di definire i requisiti della prima versione del software che volevamo produrre, tenendo però in considerazione l'importanza dell'estendibilità, al fine di non essere costretti negli Sprint successivi a riprogettare nuovamente componenti già sviluppati.  
Una volta terminata questa prima fase, l'altro obiettivo è stato quello di scegliere le tecnologie principali che avremmo utilizzato. Abbiamo immediatamente identificato come fondamentale l'uso di ``sbt`` e di ``Git``. Abbiamo deciso in comune accordo il workflow di Git di modo da poter porre le condizioni di iniziare a lavorare in modo indipendente.  
Ci siamo concordati sui plugin da utilizzare e sugli stili di Scala Test che volevamo seguire, di modo da essere conformi nel codice prodotto.  

Abbiamo deciso di produrre una versione estremamente basilare del software, introducendo solo i concetti fondamentali:
- giocatore
- nodo
- controller per la navigazione dei nodi, al fine di procedere nella storia

Non è stata sviluppata l'interfaccia grafica, che in questa fase era completamente trascurabile; usare delle stampe a linea di comando era più che sufficiente al fine di testare che la logica base del gioco funzionasse.
#### Considerazioni finali
Alla fine del primo Sprint siamo stati molto soddisfatti di vedere i progressi fatti. I task assegnati ai vari componenti del gruppo sono stati svolti in modo corretto.

### Sprint 2 (11/08/2021 - 21/08/2021)
#### Obiettivo
Partendo dalla versione molto basilare sviluppata allo sprint precedente, abbiamo cominciato ad aggiungere sempre più dettagli al core dell'applicazione:
- È stato abbandonata l'interfaccia a riga di comando e tutte le GUI sono state rimpiazzate con effettive view grafiche.
- Il giocatore è stato arricchito con delle proprietà, in particolare con le sua statistiche ed i suoi valori vitali. È poi stata sviluppata anche la view relativa all'assegnamento delle statistiche e quella dedicata alla loro visione.
- È stato introdotto il sistema di History e la relativa view. La History è una schermata che permette di poter controllare in un qualsiasi momento della storia quali nodi sono stati percorsi in passato.
- Le storie sono ora reperibili da File presenti sul filesystem anzichè dal codice sorgente stesso. È stato possibile grazie alla serializzazione offerta da Scala.
- Sono stati arricchiti i nodi della storia con delle condizioni, che permettono di limitare le strade percorribili dall'utente se quest'ultimo non rispetta determinate condizioni (per esempio possedendo un valore sufficientemente alto su una determinata statistica potrebbe sbloccarsi un percorso segreto).

#### Considerazioni finali
Alla fine del secondo Sprint siamo stati molto soddisfatti di vedere i progressi fatti.
I task assegnati ai vari componenti del gruppo sono stati svolti in modo corretto.  
È sorto un unico imprevisto non grave:
- La serializzazione era inizialmente stata pensata in un formato differente. Si era pensato di utilizzare il linguaggio json per rappresentare la storia. Utilizzare la serializzazione di Scala si è rivelato tuttavia più semplice ed efficiente. Scala dispone di alcune librerie per la serializzazione e la deserializzazione in formato json ma tutte quante soffrono di limitazioni non facilmente risolvibili.

### Sprint 3 (21/08/2021 - 31/08/2021)
#### Obiettivo
In questo sprint ci siamo innanzitutto concentrati sull'obiettivo di terminare lo sviluppo del model nella sua interezza, non necessariamente integrandolo nei controller.  
Questa scelta è stata presa da due necessità:  
- la serializzazione delle storie crea dei file che, una volta modificato il model, risultano incompatibili;
- la creazione, prevista per il prossimo sprint, dell'editor (che andrà a manipolare ogni aspetto del model).

Vi è stato poi un importante focus sulla standardizzazione di molteplici aspetti lato view.
Tra gli obiettivi raggiunti troviamo:
- salvataggio dei progressi di una storia tramite serializzazione;
- creazione di un menu principale tramite il quale poter scegliere una storia da giocare tra quelle disponibili;
- integrazione shortcut da tastiera per velocizzare la navigazione all'interno del gioco;
- integrazione effetti sonori al fine di fornire feedback audio all'utente;
- creazione di inventario e oggetti (model);
- creazione dei nemici (model).

È stata effettuata, a fine sprint, una revisione del codice prodotto fin ora al fine di garantire uno standard qualitativo elevato.  
Sotto il punto di vista ingegneristico inoltre, in questo sprint è cominciato lo studio di fattibilità per la realizzazione di un modulo prolog volto all'esplorazione di tutte le strade possibili all'interno di una storia.

#### Considerazioni finali
Questo sprint è stato molto denso di contenuti, con un notevole salto quantitativo a livello di feature introdotte. Anche sta volta i task sono stati svolti nei tempi previsti.  
Poco prima della release è sorto un bug legato al caricamento da file quando il progetto veniva lanciato da sbt piuttosto che dal run integrato in IntelliJ.  
Tramite un meeting comune siamo stati in grado di identificare il problema velocemente e porvi rimedio.

### Sprint 4 (31/08/2021 - 10/09/2021)
#### Obiettivo
Anche in questo sprint vi è stato un carico lavorativo importante.  
Utilizzando il model concluso nello sprint precedente sono state implementate le seguenti feature:
- gestione dell'inventario;
- gestione degli eventi presenti all'interno di un nodo;
- gestione dei combattimenti.

È inoltre cominciato lo sviluppo dell'editor, ottenendo una versione funzionante capace di eseguire le operazioni più basilari (le funzioni avanzate verranno implementate nel prossimo sprint).  
I file di storie prodotte dall'editor, inoltre, possono ora essere aggiunti alle storie disponibili in maniera guidata evitando che l'utente vada manualmente ad inserire i file nella cartella giusta (è stata anche implementata una funzione per l'eliminazione guidata di storie).  

Si è concluso lo studio di fattibilità del modulo prolog descritto nello sprint precedente, confermando la possibilità di realizzare le feature desiderate (che verranno implementate nel prossimo sprint).

#### Considerazioni finali
Il programma è in grado di eseguire la quasi totalità delle feature previste per la release finale, confermando le nostre previsioni sui tempi di sviluppo.  

### Sprint 5 (10/09/2021 - 20/09/2021)
#### Obiettivo
In questi 10 giorni sono stati portati a termine sostanziosi obiettivi nelle due macroaree rimanenti:
- Editor  
Sono state completate tutte le feature dell'editor rendendo possibile la creazione di una storia utilizzando esclusivamente tale tool (rendendo obsoleto il generatore randomico da codice sviluppato nei primi sprint).
- Modulo prolog  
Il modulo prolog permette di eseguire la totalità delle operazioni previste, fornendo uno strumento potente e versatile.  

Nonostante il modulo prolog sia pronto, abbiamo scelto di non includerlo in questa release in quanto è stato ritenuto necessario svolgere ulteriori verifiche prima di renderlo disponibile.  

Per quanto riguarda il core dell'applicativo sono state apportate piccole migliorie e bug fix; è stata inoltre predisposta la feature di riproduzione di brani musicali durante il gioco.

#### Considerazioni finali
Lo sviluppo dell'editor e del modulo prolog ha richiesto molto impegno per tutti i componenti del gruppo, ma fortunatamente tale impegno ha mostrato i suoi frutti.  
Secondo le nostre previsioni però nel prossimo sprint potremo recuperare le forze con compiti più leggeri in quanto tutti i requisiti del sistema sono stati raggiunti.

### Sprint 6 (date_start - date-end)
#### Obiettivo
#### Considerazioni finali

## Conclusioni
### Breve sintesi

### Sviluppi futuri