# 5 - Implementazione

## Git workflow accordato
All'inizio del progetto, un momento cruciale è stato quello della scelta del workflow da seguire su Git.  
In conseguenza alla scelta di sfruttare il meccanismo delle pull-request, ogni membro del gruppo era responsabile della propria fork, e questo ha reso ancora più fondamentale scegliere come strutturare il repository sul quale effettuare le pull-request.  
Si è deciso di utilizzare diversi branch, al fine di mantere il lavoro ben organizzato:
- __docs__: il branch docs è un branch orfano nel quale abbiamo prodotto tutta la documentazione. In questo branch non è presente infatti il codice dell'applicativo.
- __master__:  si è deciso di utilizzare il branch master solo alla fine di ogni Sprint, con l'obiettivo di rilasciare una versione funzionante del gioco, contrassegnata dal rispettivo tag; prima di rilascire la versione su master, veniva fatto il merge di tutto il codice sul branch develop, al fine di verificare il corretto funzionamento.
- __develop__: è il branch di appoggio sul quale sono stati svolti tutti gli sviluppi. Durante i primi Sprint le push non sono mai state effettuate sul branch develop ma nei suoi "sotto-branch", in quanto i task erano molto specifici di una certa area del progetto e quindi indipendenti.  
L'utilizzo del pattern di progettazione MVC descritto in precedenza ha permesso una facile suddivisione del lavoro sotto questo punto di vista.  
Per questa ragione develop è stato organizzato a livello logico nei seguenti branch:
    - model  
    - view  
    - controller  

<div align="center">
<img src="https://images2.imgbox.com/f3/a8/dSzb8hTW_o.png" alt="Workflow durante i primi 3 Sprint"/>
<p align="center">Workflow durante i primi 3 Sprint</p>
</div>

Una volta che il model è stato completato, i task hanno iniziato a riguardare porzioni di codice più dipendenti tra loro, che ad esempio coinvolgevano sia view che controller.  
Per questa ragione, dal quarto Sprint abbiamo deciso di eliminare i branch model, view e controller e continuare il lavoro su develop.  
Riteniamo che sia stata la scelta più opportuna da prendere, in quanto lavorare completamente su branch separati non permetteva più a quel punto di essere agili nell'effettuare modifiche.

## Utilizzo del paradigma funzionale

#### Limitazione dei side-effects
Si è cercato quanto più possibile di non utilizzare metodi con side effects. Si è quindi preferito utilizzare oggetti immutabili, preferendo la creazione di
nuovi rispetto alla modifica dello stato di quelli già esistenti qualora fosse possibile.

#### Utilizzo di ricorsione 
In diversi casi si è fatto uso della ricorsione, meccanismo più frequentamente utilizzato in linguaggi funzionali proprio per garantire immutabilità.
La strutture dati delle storie sono inoltre particolarmente indicate ed adatte all'utilizzo di metodi ricorsivi (basti pensare alla maggior parte degli algoritmi di esplorazione di alberi).
Una buona parte degli algoritmi che effettuano controlli ed operazioni sulla struttura dati principali sono stati quindi scritti in forma ricorsiva.
#### Utilizzo massivo del pattern Strategy
Al fine di rendere il codice il più riutilizzabile e generico possibile si è cercato di definire deli metodi in grado di accettare strategie.
Il pattern Strategy è stato utilizzato massivamente in diverse classi del progetto in modo da poter modellare diversamente alcuni comportamenti del sistema senza la necessità di modificare codice già esistente.

#### Utilizzo di Optional
Dove possibile si è preferito utilizzare campi o parametri opzionali.
Questo ha permesso la creazione di una struttura del sistema più robusta ed in grado di saper rispondere in maniera migliore ai fallimenti delle operazione.
L'uso degli Optional permette anche di azzerare completamente l'utilizzo della keyword _null_, la quale è spesso fonte di bug o comportamenti non desiderati.

#### Utilizzo del currying
Il linguaggio Scala mette a disposizione nella sua parte funzionale una funzionalità denominata currying, ovvero l'applicazione di una funzione a solo una parte dei suoi argomenti.  
I casi d'uso del currying possono essere molteplici. Durante lo sviluppo del progetto è stato particolarmente utile nella stesura del codice della classe di model _Item_ per specificare che di base il bersaglio di uno strumento è colui che lo possiede.  
Utilizzando la maniera convenzionale di creare i metodi è infatti impossibile impostare come valore di default di un parametro un altro parametro specificato precedentemente.  
``` scala
def use(owner: Character)(target: Character = owner): Unit
```
Un ulteriore beneficio nello strutturare in questo modo il metodo è che è molto più difficile dimenticarsi di impostare il parametro _target_ in quanto, nonostante sia un parametro di default, è comunque necessario utilizzare un paio di parentesi addizionale per richiamare la funzione.
Dimenticarsi di impostare un parametro di default in un metodo scritto in maniera convenzionale risulta invece più semplice, e può essere fonte di bug.

## Utilizzo del paradigma logico
Come requisito opzionale il team si era proposto di realizzare uno strumento in grado di fornire dati su una determinata storia in Prolog, realizzando una sorta di "esploratore di storie".

#### Requisiti
Si desidera realizzare una sorta di "esploratore", in grado di percorrere tutti i possibili percorsi presenti all'interno di una storia.
L'idea iniziale consisteva nel mostrare solo quali nodi sono stati attraversati in ciascun percorso intrapreso.  
L'esploratore è stato però arricchito, e nella sua versione finale possiede le seguenti funzionalità:
- Acquisizione della narrazione di un nodo a partire dal suo id.
- Acquisizione della descrizione di un pathway dato l'id del nodo di partenza e l'id del nodo di arrivo.
- Acquisizione dei percorsi possibili dato l'id del nodo di partenza e l'id del nodo di arrivo.
- Acquisizione dei percorsi possibili a partire dal nodo radice (inizio della storia) o da un determinato nodo successivo. 
- Acquisizione di tutte le narrazioni dei nodi e delle descrizioni dei pathway dei percorsi possibili (in ordine) a partire dal nodo radice (inizio della storia) o da un determinato nodo successivo verso tutti i nodi finali.

Iterando su tutte le soluzione si ottiene gratuitamente anche quante sono in totale (vale ovviamente per ciascun predicato).
#### Integrazione in Scala

##### Prolog Engine
È stata sviluppata una classe Scala che, grazie all'API della libreria tuProlog, a partire dalla teoria passata e da un termine in input è in grado di restituire uno Stream di risultati in output.  
L'output rappresenta le soluzioni trovate dall'engine che sono poi facilmente manipolabili grazie alle strutture personalizzate create.
##### Strutture
Sono state create diverse classi Scala (una per ogni predicato), per semplificare la creazione e la manipolazione dei termini.  
L'API offerta è infatti stata sviluppata per essere il più generale possibile. Lavorare sui risultati grezzi risulta essere molto macchinoso, verboso e spesso è poco comprensibile l'intento o l'obiettivo che si vuole raggiungere.
Il recupero dei dati e la loro conversione è quindi stata incapsulata e nascosta all'interno di queste classi in modo da rendere più semplice e rapido l'utilizzo dall'esterno, oltre che a favorire il principio DRY.

##### Teoria generata
Per poter lavorare su storie già esistenti è stato necessaria una sorta di mapping da classi (Scala) a fatti (Prolog).  
Tramite l'utilizzo di impliciti sono stati aggiunti metodi alle strutture dati principali dell'applicazione per convertirle in stringhe rappresentanti dei fatti. Uno StoryNode contenente dei Pathway viene quindi rappresentato nel seguente modo:
``` prolog
% story_node(I,N,P)
story_node(0, 'narrative', [pathway(1, 'description'), ...])
```
Ogni StoryNode di una storia viene convertito in una stringa di questo tipo, la lista ottenuta viene utilizzata dal Prolog Engine per generare parte della sua teoria.
##### Teoria sviluppata
Oltre ai fatti generati a partire dalle strutture già presenti su Scala sono stati inseriti all'interno del Prolog Engine anche i fatti e i predicati presenti all'interno del file ``theory.pl`` situato all'interno della cartella delle risorse.  
Al suo interno sono presenti i predicati che rappresentano l'effettiva logica che permette di ottenere le soluzioni desiderate.
## Test

L’utilizzo di test si è rivelato fondamentale per la corretta realizzazione del progetto. Si è cercato di scrivere test che
premettessero di garantire qualità del sistema realizzato e che fossero anche utilizzabili come documentazione dello stesso. 

### Metodologie di testing

#### Test Automatizzati
Per quanto riguarda il testing automatizzato si è cercato si seguire un approccio quanto più possibile TDD (Test Driven Development), cercando di seguire il ciclo Red - Green - Refactor. Questo è stato possibile soprattuto nelle fasi inziali del progetto, dove la maggior parte delle classi sviluppate appartenevano al model.  
Nelle fasi successive questo approccio è venuto meno. Diversi test sono stati realizzati seguendo un approccio più tradizionale, meno stringente di quanto richiesto normalmente dalle politiche del TDD.  
Inoltre alcuni componenti del sistema sono risultati particolarmente tediosi da testare in modo automatizzato a causa dell'incapsulamento dello stato, il quale non è spesso visibile dall'esterno. Lo strumento utilizzato per effettuare il testing è [ScalaTest](#scalatest).
In particolare è stata utilizzata la specifica _FlatSpec_ come stile dei test prodotti.

##### Suites di Testing
Tra le funzionalità di ScalaTest utilizzate vi sono le [Suites](https://www.scalatest.org/scaladoc/1.8/org/scalatest/Suites.html).
Ogni classe di Test creata è effettivamente già una Suite di test.
È possibile però creare classi che estendono Suites, specificando come argomenti altre Suite.
Questo permette non solo una migliore separazione dei concetti, ma in determinati casi permette di limitare la duplicazione di codice (applicando il principio DRY).  
La classe di Test _FileSystemSuite_ per esempio esegue due suite di test che hanno una cosa in comune: entrambe hanno bisogno di una cartella all'interno del file system per creare e manipolare file.  
Grazie alla trait _BeforeAndAfterAll_ è possibile creare una cartella dedicata a questi test nella directory di Temp della macchina che li esegue e cancellarla una volta terminata l'operazione.
##### Tags
I [Tag](https://www.scalatest.org/user_guide/tagging_your_tests) sono un'altra funzionalità di ScalaTest che permette di assegnare un tag (un'etichetta) a determinati test. Questo permette non solo una migliore catalogazione, ma anche di eseguire (o ignorare) determinati test utilizzando comandi specifici.  
Questa funzione è stata molto utile per impedire a determinati test di venir eseguiti tramite GitHub Action, in quanto alcuni test hanno necessità di renderizzare View o caricare e riprodurre suoni.  
Alcuni test, funzionanti su macchine locali, se eseguiti tramite GitHub actions erano infatti causa di  eccezioni per colpa di limitazione imposte dall'ambiente virtuale.

##### Coverage
È stato utilizzato lo strumento Scoverage per verificare la percentuale di codice attraversato dai test rispetto al totale della code base.  
Come illustrato [precedentemente](#test-automatizzati) non è stato possibile automatizzare i test per ogni componente del sistema, quindi sono stati esclusi alcuni file sorgenti e alcuni package dall'analisi della coverage.

#### Test non Automatizzati
Per alcuni componenti del sistema non è stato possibile creare test automatizzati. Sono comunque state realizzate delle sessioni di testing in cui si è scandagliato il sistema in funzione per cercare bug, inesattezze o comportamenti non attesi.  
Durante queste sessioni, verificatesi soprattuto durante gli sprint finali, si è cercato di portare il sistema in molti casi limite per verificare che il comportamento effettivo fosse anche quello previsto.

## Suddivisione del lavoro
<!-- NOTA: per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design)-->
### Filaseta Angelo
<!-- Lista di componenti -->
### Sanchi Piero
<!-- Lista di componenti -->
### Talmi Alessandro
<!-- Lista di componenti -->
### Tronetti Elisa
<!-- Lista di componenti -->

---
