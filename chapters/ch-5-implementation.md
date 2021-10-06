# 5 - Implementazione

## 5.1 - Git workflow accordato
All'inizio del progetto, un momento cruciale è stato quello della scelta del workflow da seguire per cooperare su Git.  
In conseguenza alla scelta di sfruttare il meccanismo delle pull-request, ogni membro del gruppo è stato responsabile della propria fork, e questo ha reso ancora più fondamentale scegliere come strutturare il repository sul quale effettuare le pull-request.  
Si è deciso di utilizzare diversi branch, al fine di mantere il lavoro ben organizzato:
- __docs__: il branch docs è un branch orfano nel quale abbiamo prodotto tutta la documentazione. In questo branch non è presente infatti il codice dell'applicativo.
- __master__:  si è deciso di utilizzare il branch master solo alla fine di ogni Sprint, con l'obiettivo di rilasciare una versione funzionante del gioco, contrassegnata dal rispettivo tag; prima di rilascire la versione su master, veniva fatto il merge di tutto il codice sul branch develop, al fine di verificarne il corretto funzionamento.
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

## 5.2 - Utilizzo del paradigma funzionale

### 5.2.1 - Limitazione dei side-effects
Si è cercato quanto più possibile di non utilizzare metodi con side effects. Si è quindi preferito utilizzare oggetti immutabili, preferendo la creazione di
nuovi rispetto alla modifica dello stato di quelli già esistenti qualora fosse possibile.

### 5.2.2 - Utilizzo di ricorsione 
In diversi casi si è fatto uso della ricorsione, meccanismo più frequentemente utilizzato in linguaggi funzionali proprio per garantire immutabilità.  
La strutture dati delle storie sono inoltre particolarmente indicate ed adatte all'utilizzo di metodi ricorsivi (basti pensare alla maggior parte degli algoritmi di esplorazione di alberi).  
Una buona parte degli algoritmi che effettuano controlli e operazioni sulla struttura dati principali sono stati quindi scritti in forma ricorsiva.

### 5.2.3 - Utilizzo massivo del pattern Strategy
Al fine di rendere il codice il più riutilizzabile e generico possibile si è cercato di definire dei metodi in grado di accettare strategie.  
Il pattern Strategy è stato utilizzato massivamente in diverse classi del progetto in modo da poter modellare diversamente alcuni comportamenti del sistema senza la necessità di modificare codice già esistente.

### 5.2.4 - Utilizzo di Optional
Dove possibile si è preferito utilizzare campi o parametri opzionali.  
Questo ha permesso la creazione di una struttura del sistema più robusta e in grado di saper rispondere in maniera migliore ai fallimenti delle operazioni.  
L'uso degli Optional permette anche di azzerare completamente l'utilizzo della keyword _null_, la quale è spesso fonte di bug o comportamenti non desiderati.

### 5.2.5 - Utilizzo del currying
Il linguaggio Scala mette a disposizione nella sua parte funzionale una funzionalità denominata currying, ovvero l'applicazione di una funzione a solo una parte dei suoi argomenti.  
I casi d'uso del currying possono essere molteplici. Durante lo sviluppo del progetto è stato particolarmente utile nella stesura del codice della classe di model _Item_ per specificare che di base il bersaglio di uno strumento è colui che lo possiede.  
Utilizzando la maniera convenzionale di creare i metodi è infatti impossibile impostare come valore di default di un parametro un altro parametro specificato precedentemente.  
``` scala
def use(owner: Character)(target: Character = owner): Unit
```
Un ulteriore beneficio nello strutturare in questo modo il metodo è che è molto più difficile dimenticarsi di impostare il parametro _target_ in quanto, nonostante sia un parametro di default, è comunque necessario utilizzare un paio di parentesi addizionale per richiamare la funzione.
Dimenticarsi di impostare un parametro di default in un metodo scritto in maniera convenzionale risulta invece più semplice, e può essere fonte di bug.

## 5.3 - Utilizzo del paradigma logico
Come requisito opzionale il team si era proposto di realizzare uno strumento in grado di fornire dati su una determinata storia in Prolog, realizzando una sorta di "esploratore di storie".

### 5.3.1 - Requisiti
Si desidera realizzare una sorta di "esploratore", in grado di percorrere tutti i possibili percorsi presenti all'interno di una storia.  
L'idea iniziale consisteva nel mostrare solo quali nodi sono stati attraversati in ciascun percorso intrapreso.  
L'esploratore è stato però arricchito, e nella sua versione finale possiede le seguenti funzionalità:
- Acquisizione della narrazione di un nodo a partire dal suo id.
- Acquisizione della descrizione di un pathway dato l'id del nodo di partenza e l'id del nodo di arrivo.
- Acquisizione dei percorsi possibili dato l'id del nodo di partenza e l'id del nodo di arrivo.
- Acquisizione dei percorsi possibili a partire dal nodo radice (inizio della storia) o da un determinato nodo successivo. 
- Acquisizione di tutte le narrazioni dei nodi e delle descrizioni dei pathway dei percorsi possibili (in ordine) a partire dal nodo radice (inizio della storia) o da un determinato nodo successivo verso tutti i nodi finali.

Iterando su tutte le soluzioni si ottiene gratuitamente anche quante sono in totale (vale ovviamente per ciascun predicato).

### 5.3.2 - Integrazione in Scala
#### 5.3.2.1 - Prolog Engine
È stata sviluppata una classe Scala che, grazie all'API della libreria tuProlog, a partire dalla teoria passata e da un termine in input è in grado di restituire uno Stream di risultati in output.  
L'output rappresenta le soluzioni trovate dall'engine che sono poi facilmente manipolabili grazie alle strutture personalizzate create.

#### 5.3.2.2 - Strutture
Sono state create diverse classi Scala (una per ogni predicato), per semplificare la creazione e la manipolazione dei termini.  
L'API offerta è infatti stata sviluppata per essere il più generale possibile. Lavorare sui risultati grezzi risulta essere molto macchinoso, verboso e spesso è poco comprensibile l'intento o l'obiettivo che si vuole raggiungere.  
Il recupero dei dati e la loro conversione è quindi stata incapsulata e nascosta all'interno di queste classi in modo da rendere più semplice e rapido l'utilizzo dall'esterno, oltre che a favorire il principio DRY.

#### 5.3.2.3 - Teoria generata
Per poter lavorare su storie già esistenti è stato necessaria una sorta di mapping da classi (Scala) a fatti (Prolog).  
Tramite l'utilizzo di impliciti sono stati aggiunti metodi alle strutture dati principali dell'applicazione per convertirle in stringhe rappresentanti dei fatti. Uno StoryNode contenente dei Pathway viene quindi rappresentato nel seguente modo:
``` prolog
% story_node(I,N,P)
story_node(0, 'narrative', [pathway(1, 'description'), ...])
```
Ogni StoryNode di una storia viene convertito in una stringa di questo tipo, la lista ottenuta viene utilizzata dal Prolog Engine per generare parte della sua teoria.
#### 5.3.2.4 - Teoria sviluppata
Oltre ai fatti generati a partire dalle strutture già presenti su Scala sono stati inseriti all'interno del Prolog Engine anche i fatti e i predicati presenti all'interno del file ``theory.pl`` situato all'interno della cartella delle risorse.  
Al suo interno sono presenti i predicati che rappresentano l'effettiva logica che permette di ottenere le soluzioni desiderate.

## 5.4 -Test

L’utilizzo di test si è rivelato fondamentale per la corretta realizzazione del progetto.  
Si è cercato di scrivere test che premettessero di garantire la qualità del sistema realizzato e che fossero anche utilizzabili come documentazione dello stesso. 

### 5.4.1 - Test Automatizzati
Per quanto riguarda il testing automatizzato si è cercato si seguire un approccio quanto più possibile TDD (Test Driven Development), cercando di seguire il ciclo Red - Green - Refactor. Questo è stato possibile soprattuto nelle fasi inziali del progetto, dove la maggior parte delle classi sviluppate appartenevano al model.  
Nelle fasi successive questo approccio è venuto meno. Diversi test sono stati realizzati seguendo un approccio più tradizionale, meno stringente di quanto richiesto normalmente dalle politiche del TDD.  
Inoltre alcuni componenti del sistema sono risultati particolarmente tediosi da testare in modo automatizzato a causa dell'incapsulamento dello stato, il quale non è spesso visibile dall'esterno.  

Lo strumento utilizzato per effettuare il testing è [ScalaTest](#scalatest).
In particolare è stata utilizzata la specifica _FlatSpec_ come stile dei test prodotti.

#### 5.4.1.1 - Suites di Testing
Tra le funzionalità di ScalaTest utilizzate vi sono le [Suites](https://www.scalatest.org/scaladoc/1.8/org/scalatest/Suites.html).
Ogni classe di Test creata è effettivamente già una Suite di test.
È possibile però creare classi che estendono Suites, specificando come argomenti altre Suite.
Questo permette non solo una migliore separazione dei concetti, ma in determinati casi permette di limitare la duplicazione di codice (applicando il principio DRY).  
La classe di Test _FileSystemSuite_ per esempio esegue due suite di test che hanno una cosa in comune: entrambe hanno bisogno di una cartella all'interno del file system per creare e manipolare file.  
Grazie alla trait _BeforeAndAfterAll_ è possibile creare una cartella dedicata a questi test nella directory di Temp della macchina che li esegue e cancellarla una volta terminata l'operazione.
#### 5.4.1.2 - Tags
I [Tag](https://www.scalatest.org/user_guide/tagging_your_tests) sono un'altra funzionalità di ScalaTest che permette di assegnare un tag (un'etichetta) a determinati test. Questo permette non solo una migliore catalogazione, ma anche di eseguire (o ignorare) determinati test utilizzando comandi specifici.  
Questa funzione è stata molto utile per impedire a determinati test di venir eseguiti tramite GitHub Action, in quanto alcuni test hanno necessità di renderizzare View o caricare e riprodurre suoni.  
Alcuni test, funzionanti su macchine locali, se eseguiti tramite GitHub Action erano infatti causa di eccezioni per colpa di limitazioni imposte dall'ambiente virtuale.

#### 5.4.1.3 - Coverage
È stato utilizzato lo strumento Scoverage per verificare la percentuale di codice attraversato dai test rispetto al totale della code base.  
Come illustrato [precedentemente](#test-automatizzati) non è stato possibile automatizzare i test per ogni componente del sistema, quindi sono stati esclusi alcuni file sorgenti e alcuni package dall'analisi della coverage.

### 5.4.2 - Test non Automatizzati
Per alcuni componenti del sistema non è stato possibile creare test automatizzati. Sono comunque state realizzate delle sessioni di testing in cui si è scandagliato il sistema in funzione per cercare bug, inesattezze o comportamenti non attesi.  
Durante queste sessioni, verificatesi soprattuto durante gli sprint finali, si è cercato di portare il sistema in molti casi limite per verificare che il comportamento effettivo fosse sempre quello previsto.

## 5.5 - Suddivisione del lavoro
### 5.5.1 - Filaseta Angelo
Durante lo sviluppo del progetto mi sono occupato di diversi aspetti molto variegati:

1. In una fase preliminare del progetto mi sono occupato dell'impostazione dei parametri del tool sbt. Mi sono occupato dell'aggiunta delle dipendenze e dei plugin principali (ScalaTest, Scoverage, sbt-github-action, sbt-assembly) e della relativa configurazione.
   
2. Nella fase iniziale del progetto ho realizzato insieme a [Talmi](#talmi-alessandro) la struttura principale dei controller di gioco, ovvero il modo in cui i controller interagiscono tra loro all'interno dell'attuale package __controller.game__.

3. Mi sono poi autonomamente occupato della creazione della struttura di componenti di utilità per il recupero di risorse e per la serializzazione, realizzando la maggior parte del package __controller.util__.
   
4. Nella fase intermedia del progetto ho realizzato insieme a [Tronetti](#tronetti-elisa) la parte di Model relativa al package __model.items__. Una volta terminata ho potuto dedicarmi alla creazione del controller __controller.game.subcontroller.InventoryController__ e della relativa view contenuta nel package __view.inventory__ grazie anche all'aiuto di [Sanchi](#sanchi-piero).
   
5. Successivamente ho aiutato [Talmi](#talmi-alessandro) nella realizzazione dell'editor creando i componenti all'interno del package __view.form__. Il package contiene strumenti utili per le View che necessitano di richiedere alcune informazioni all'utente tramite appunto dei Form. Il componente principale __view.form.FormBuilder__ è stato successivamente utilizzato anche all'esterno dell'Editor. Le classi sono strutturate in modo da effettuare anche controlli sull'input e su eventuali stati del sistema dipendenti dai valori inseriti.
   
6. Insieme a [Tronetti](#tronetti-elisa) ho realizzati alcuni predicati Prolog. Una volta ottenuta una parte di logica funzionante mi sono occupato di creare i componenti all'interno del package __controller.prolog__ facendo quindi interoperare i due linguaggi. Una volta ottenuto un motore completamente funzionante ho potuto stendere il controller __controller.ExplorerController__ e la relativa view contenuta nel package __view.explorer__.
   
7. In una fase finale del progetto mi sono occupato della risoluzione di alcuni bug minori riscontrati e ho effettuato piccole operazioni di refactor ai componenti che presentavano inconsistenze nei termini utilizzati.

Durante tutta la realizzazione del progetto inoltre mi sono occupato della stesura dei test relativi ai sorgenti da me realizzati, utilizzando anche meccanismi di ScalaTest come le Suites e i Tag.

### 5.5.2 - Sanchi Piero

### 5.5.3 - Talmi Alessandro
Durante questi due mesi ho avuto modo di lavorare a molteplici aspetti del progetto:

1. Per prima cosa, insieme a [Tronetti](#tronetti-elisa), [Filaseta](#filaseta-angelo) e [Sanchi](#sanchi-piero), ho condotto un'analisi del problema, del dominio applicativo e delle possibili tecnologie coinvolte.  
Ciò ci ha portato ad effettuare una __progettazione preliminare__ utile a porre le fondamenta del progetto.  
L'aspetto più fondamentale di questa fase è stata la produzione di __modellistica__ (la stessa che, evolutasi nel tempo, compone parte di questa relazione).

2. In fase di setup mi sono occupato dello studio e dell'integrazione del plugin __Scalafix__.  
Collaborando con [Tronetti](#tronetti-elisa), inoltre, ho effettuato ricerche sulle modalità di gestione del __Git workflow__; ciò si è rivelato fondamentale in quanto il repository principale, dalla quale il resto dei componenti del team avrebbe creato delle fork, è stato gestito da me (in collaborazione con [Filaseta](#filaseta-angelo) nel caso in cui non fossi stato momentaneamente reperibile).

3. Terminate le fasi preliminari, in collaborazione con [Tronetti](#tronetti-elisa) ho realizzato le interfacce e le implementazioni di __StoryNode__ e __StoryModel__.  
All'interno dello stesso sprint inoltre, affiancato da [Filaseta](#filaseta-angelo), ho realizzato la struttura principale dei controller di gioco, predisponendo l'architettura estendibile basata su __GameMasterController__ e molteplici __SubController__.

4. In autonomia ho successivamente realizzato la feature della __History__ (log) di una storia, sviluppando i necessari componenti di model, view e controller; al fine di poter ottenere rapidamente delle storie, seppur basilari, ho implementato un __generatore randomico__ che sfruttasse le classi da noi create. Ho inoltre integrato all'interno dei Pathway la possibilità di introdurre dei __prerequisiti__.  
Ho avuto poi modo di concentrarmi sulla view dedicata alla __visualizzazione delle info del giocatore__; da qui ho cominciato a coordinarmi con [Sanchi](#sanchi-piero) per ottenere __uniformità nei moduli che sarebbero andati a comporre le varie view__.

5. Terminato il processo per ottenere uniformità all'interno del comparto view, sfruttando i componenti per la serializzazione implementati da [Filaseta](#filaseta-angelo), ho implementato la funzione di __salvataggio dei progressi di una storia__.  
Ho infine posto le basi per la classe Event.

6. Da questo punto in poi mi sono allontanato dallo sviluppo del gioco (tornandovi solo per modifiche minori o per fornire aiuto ai miei compagni) e mi sono dunque concentrato sulla realizzazione di un __editor__ che permettesse la creazione di nuove storie agilmente attraverso l'interfaccia grafica direttamente dall'interno dell'applicativo in esecuzione.  
Innanzitutto vi è stata una prima fase in cui ho dovuto effettuare ricerche e test al fine di trovare una libreria grafica che permettesse di esprimere in modo chiaro il grafico di una storia.  
Successivamente ho convenuto che, per questa particolare feature, fosse conveniente creare una versione mutabile delle principali classi che compongono la struttura di una storia.  
Lo sviluppo dell'editor è stato un processo particolarmente lungo, dettato non tanto dalle molte operazioni eseguibili durante la creazione e la modifica di una storia, quanto più dai controlli logici per prevedere tutte le situazioni in cui una storia poteva rompere i vincoli da noi imposti; è stato inoltre necessario ottimizzare gli algoritmi usati in questa feature, in quanto l'esplorazione della struttura di una storia per eseguirvi sopra delle operazioni poteva richiedere tempi computazionali spesso non trascurabili. 

7. Durante tutto lo svolgimento del progetto si è cercato di mantenere uno standard qualitativo alto nell'organizzazione e implementazione del codice; per ulteriormente migliorare tale aspetto nelle fasi finali del progetto (insieme ai miei compagni) mi sono dedicato alla __revisione__ di quanto fatto finora alla ricerca di potenziali migliorie.

Durante questi mesi di lavoro mi sono occupato dei __test__ riguardanti alcuni componenti da me sviluppati, oltre che aver sempre effettuato una prima revisione del codice durante l'approvazione delle pull request effettuate dai miei compagni.

### 5.5.4 - Tronetti Elisa

Durante lo svolgimento di questo progetto ho affrontato diversi aspetti di progettazione e sviluppo:  

1. Nel corso del primo Sprint mi sono occupata della ricerca approfondita di informazioni su __ScalaTest__, al fine di proporre agli altri membri del gruppo la soluzione più appropriata per il nostro progetto.  
Insieme a [Talmi](#talmi-alessandro) abbiamo discusso l'organizzazione del __Git Workflow__ da seguire e abbiamo valutato i vantaggi e gli svantaggi dell'utilizzo delle __pull request__ per lo svolgimento del progetto.  
Infine ho eseguito il set-up della board su __Trello__, cercando di creare una board organizzata e chiara; ho predisposto le varie liste, le label che avrebbero costituito il punto di riferimento per l'identificazione dei task e ho creato dei bottoni che automatizzavano il reset delle liste alla fine di ogni Sprint, di modo da non doverlo svolgere manualmente.  

2. Sempre durante il primo Sprint, insieme a tutti i membri del gruppo, ho partecipato all'analisi del dominio applicativo e alla conseguente progettazione.  

3. Il primo Sprint mi ha vista coinvolta insieme a [Talmi](#talmi-alessandro) nell'implementazione di __StoryNode__ e __StoryModel__. Ho inoltre lavorato in autonomia a __Pathway__, di modo da completare le classi che costituiscono struttura dati di base della storia.  

4. Durante il secondo Sprint ho lavorato alle proprietà dei personaggi e nello specifico a __Stats__ e __StatsModifier__, le quali permetto a un giocatore di avere le caratteristiche che potranno poi essere influenzate da eventi esterni.  

5. Durante il terzo Sprint ho implementato insieme a [Filaseta](#filaseta-angelo) i vari tipi di oggetti, e nello specifico tutto ciò che è contenuto nel package __model.items__.  

6. Nel quarto Sprint mi sono occupata della creazione del meccanismo delle battaglie con i nemici, creando sia il __BattleController__ che la __BattleView__. L'implementazione della BattleView ha richiesto la generalizzazione di alcuni componenti grafici già esistenti e la creazione di elementi propri di questa interfaccia grafica.  
Il meccanismo della battaglia ha richiesto anche un'integrazione con l'__InventoryController__ implementato da [Filaseta](#filaseta-angelo); abbiamo quindi ragionato insieme per decidere come fare interagire correttamente le due logiche.  
Il calcolo dei danni, della probabilità di fuggire dalla battaglia e di fallire l'attacco si sono evolute nel tempo, al fine di non renderlo banale e di fare in modo che il giocatore non possa utilizzare un'unica statistica per essere certo di poter vincere qualunque battaglia.  

7. Il quinto Sprint mi ha vista coinvolta insieme a [Filaseta](#filaseta-angelo) nella realizzazione dei predicati __Prolog__ per l'esplorazione delle storie. Ho utilizzato il software tuProlog per implementare i vari predicati e verificarne il funzionamento. Una volta che il risultato era quello voluto, [Filaseta](#filaseta-angelo) si è occupato della sua integrazione con Scala.  

8. L'ultimo Sprint ci ha visti tutti coinvolti nella ricerca di bug e nel refactor del codice.  
Mi sono occupata principalmente di risolvere bug relativi all'inventario, alla battaglia e all'aggiornamento delle view quando le statistiche del giocatore venivano modificate.  
Inoltre ho effettuato un refactor abbastanza minuzioso dei test, al fine di ridurre le ripetizioni di codice che li rendevano difficili da leggere.  

9. Essendo stata la responsabile di Trello durante questo progetto, nell'ultimo Sprint mi sono anche dedicata nel produrre le tabelle dello __Scrum Backlog__ prensenti anche in questa documentazione, estraendo le informazioni principali dalla board di Trello.

Nello svolgimento del progetto mi sono occupata dell'implementazione di test con __ScalaTest__ di tutti le classi di model da me prodotte, al fine di seguire nel modo più fedele possibile un approccio TDD.  

---
