# 3 - Design architetturale
Questo capitolo punta a descrivere il design architetturale del sistema ad alto livello, per poi riprendere una vista ancora più dettagliata nel capitolo 4.  

## 3.1 - Architettura complessiva

<div align="center">
    <img src="https://images2.imgbox.com/b2/c1/oq4wqOwC_o.png" alt="Vista ad alto livello StoryNodes e Pathways">
    <p align="center">Architettura complessiva ad alto livello</p>
</div>

L'architettura del sistema segue il pattern MVC, con Controller multipli (ognuno associato ad una View) che manipolano però lo stesso Model.

## 3.2 - Descrizione di pattern architetturali utilizzati

### 3.2.1 - Pattern Model View Controller (MVC)

Model-view-controller è un pattern architetturale in grado di separare la logica di presentazione dei dati (di cui si occupa la view) dalla logica di business (di cui si occupano model e controller), utilizzando una architettura multi-tier (basata quindi sui tre livelli da cui prende il nome).

- Model: si occupa di catturare i comportamenti di sistema in termini di dominio del problema, al suo interno pertanto sono definiti dati e logica dell'applicativo.
- View: si occupa di rappresentare le informazioni trattate che interessano l'utente in diversi modi, nel caso di
  ScalaQuest diverse GUI consentono la navigazione dei nodi durante l'attività di gioco e la visione della struttura vera e propria delle storie all'interno dell'editor.
- Controller (nel nostro caso molteplici):  ha il compito di accettare l'input che generalmente deriva dalla view e convertirlo in comandi per il model o per la view stessa.

### 3.2.2 - Model
Ad alto livello possiamo inquadrare ogni storia come un grafo contenente nodi (**StoryNode**) interconnessi da archi (**Pathway**) unidirezionali.  
Il giocatore si muoverà nella storia spostandosi tra i vari StoryNode scegliendo quali Pathway intraprendere.

Ogni StoryNode conterrà al suo interno una *narrative*, identificabile come lo sviluppo testuale della storia che il giocatore potrà leggere; ogni nodo inoltre potrebbe contenere:
- un nemico da affrontare;
- degli eventi da sviluppare;
- dei Pathway da esplorare per continuare la storia.

Questi ultimi conterranno una *description* testuale ed uno StoryNode di destinazione, oltre che un eventuale prerequisito da soddisfare di modo che il Pathway sia selezionabile.

<div align="center">
    <img src="https://images2.imgbox.com/de/81/SPwka4tu_o.png" alt="Vista ad alto livello StoryNodes e Pathways">
    <p align="center">Vista ad alto livello StoryNodes e Pathways</p>
</div>

La struttura appena descritta verrà referenziata da uno **StoryModel**, nel quale saranno contenuti:
- il nome della storia;
- le informazioni riguardanti il giocatore;
- un riferimento allo StoryNode corrente;
- un riferimento agli StoryNode visitati precedentemente di modo che sia ricostruibile uno storico.  

Affinchè la struttura di una storia sia ritenuta valida, è stato imposto un vincolo per cui non possano essere presenti cicli all'interno di una storia; uno StoryNode visitato dunque non potrà mai più essere visitato (non esisteranno strade successive che torneranno nuovamente al nodo già visitato).

<div align="center">
    <img src="https://images2.imgbox.com/54/66/WePb9Out_o.png" alt="Vista ad alto livello StoryModel">
    <p align="center">Vista ad alto livello StoryModel</p>
</div>

Segue diagramma delle classi ad alto livello del model (una vista più approfondità di tale diagramma verrà affrontata nel capitolo 4).

<div align="center">
    <img src="https://images2.imgbox.com/29/51/kKkzPT1V_o.png" alt="Diagramma classi Model ad alto livello">
    <p align="center">Diagramma classi Model ad alto livello</p>
</div>

### 3.2.3 - Controller

All'interno del sistema sono presenti più controller; ognuno di essi è associato ad una differente View mentre, per quanto riguarda il Model, le classi da manipolare sono le stesse in comune tra tutti i controller. L'effettivo controllo viene acquisito da un diverso controller dipendentemente dai servizi necessari in un dato momento.  
Durante l'esecuzione del programma, il primo controller a prendere la parola è l'ApplicationControllerSingleton; la scelta di utilizzare un singleton deriva dal fatto che esisterà sempre una sola e unica istanza di tale controller in quanto esso si trova sulla cima della gerarchia di tutti i controller.

<div align="center">
    <img src="https://images2.imgbox.com/ca/14/qUVMfwc3_o.png" alt="Gerarchia dei controller">
    <p align="center">Gerarchia dei controller</p>
</div>

Partendo dunque dall'ApplicationController, il flusso d'esecuzione può intraprendere differenti direzioni dipendentemente dalle scelte compiute dall'utente:

- Selezione di una storia  
  L'utente seleziona, attraverso il menu principale, una storia che intende giocare. Dipendentemente dalla presenza o meno di un salvataggio collegato alla storia selezionata, il flusso di controllo si divide in due diramazioni:
    - Nuova partita: questa scelta sarà sempre disponibile, indipendentemente dalla presenza di un salvataggio.  
      Il controllo viene inizialmente passato al PlayerConfigurationController che, insieme alla View collegata,
      permette all'utente di creare il proprio personaggio.  
      Tramite l'input fornito dall'utente viene creato un Player che, insieme alla struttura di StoryNode (recuperata dall'ApplicationControllerSingleton), viene utilizzato per creare uno StoryModel, passando il controllo al GameMasterController.
    <div align="center">
        <img src="https://images2.imgbox.com/da/c3/RgRPV8vL_o.png" alt="Diagramma di sequenza - nuova partita">
        <p align="center">Diagramma di sequenza - nuova partita</p>
    </div>

    - Continua partita : differentemente dalla precedente, questa opzione sarà disponibile solo in presenza di un
      salvataggio.  
      In questo caso l'utente non avrà necessità di creare un nuovo personaggio in quanto esso sarà già stato creato in una sessione di gioco precedente.  
      L'ApplicationControllerSingleton dunque reperirà la storia e il salvataggio annesso ricreando lo StoryModel e
      riportando il giocatore nella stessa situazione in cui egli aveva salvato il proprio progresso; lo StoryModel
      ricostruito verrà dunque utilizzato dal GameMasterController per continuare il gioco.
      <div align="center">
          <img src="https://images2.imgbox.com/73/3f/X6GPoddK_o.png" alt="Diagramma di sequenza - continua partita">
          <p align="center">Diagramma di sequenza - continua partita</p>
      </div>

  Entrambi i flussi di esecuzione condurranno quindi al GameMasterController, così da poter permettere all'utente di
  giocare.  
  Il GameMasterController dispone di molteplici SubController; ognuno dei quali utilizza il GameMasterController come "ponte" richiamando il metodo executeOperation e specificando quale altro SubController desidera passare il
  controllo.  
  Quando il GameMasterController riceverà una richiesta di esecuzione di un'operazione, reperirà il SubController specificato e garantirà ad esso il controllo.
    <div align="center">
        <img src="https://images2.imgbox.com/17/b7/4LOzH674_o.png" alt="Diagramma di sequenza - esecuzione di una partita">
        <p align="center">Diagramma di sequenza - esecuzione di una partita</p>
    </div>
    In questo esempio per semplicità sono stati inclusi solo lo StoryController (responsabile della navigazione all'interno dei nodi) e l'InventoryController, ma tale meccanismo è espandibile con un numero di controller a piacere rendendo estremamente facile introdurne di nuovi.  

- Utilizzo dell'editor  
  Nel momento in cui l'utente selezioni l'editor, verrà aperta una dialog che chiederà se si voglia continuare a modificare una storia (caricandola da file) o se invece se ne voglia creare una nuova. Quale che sia la scelta dell'utente verrà creata una gerarchia di StoryNode (creandone una nuova o ricostruendola da file) sulla quale l'utente potrà eseguire operazioni attraverso la View.  

  Vi sono in questo caso un solo Controller ed una sola View; per l'effettiva manipolazione dei dati, una volta che l'utente avrà selezionato l'operazione che intende compiere, la View creerà dei form attraverso il componente FormBuilder da noi implementato al fine di permettere all'utente di inserire dell'input.

### 3.2.4 - View
L'organizzazione del lato View è molto semplice: esiste un'unica interfaccia (chiamata per l'appunto View) che vincola le sue implementazioni concrete ad implementare il metodo render.  

Ogni View lavorerà in concomitanza con un'implementazione concreta di Controller.  
Quando verrà chiamato il metodo execute su un Controller, quest'ultimo richiamerà il metodo render della View concreta associata.

<div align="center">
<img src="https://images2.imgbox.com/16/17/fzT9Bn5Q_o.png" alt="Diagramma classi alto livello - View">
<p align="center">Diagramma classi alto livello - View</p>
</div>

## 3.3 - Scelte tecnologiche cruciali ai fini architetturali
### 3.3.1 - Tecnologie scartate
In questa sezione verranno illustrate alcune tecnologie che in fase di analisi sono state prese in considerazione e successivamente abbandonate. Seguono descrizioni e motivazioni.
### 3.3.2 - Modello ad attori (Akka)
Durante la progettazione della parte dei Controller sono state riscontrate alcune similarità tra il modello proposto (quello finale, già illustrato precedentemente) e il [modello ad attori](https://doc.akka.io/docs/akka/current/typed/actors.html#:~:text=com%2Fakka%2Fakka-,Akka%20Actors,correct%20concurrent%20and%20parallel%20systems).
Per quanto riguarda la fase di gioco infatti, nel modello proposto un Controller principale (_GameMasterController_) si occupa di creare i sub controller e funge da loro coordinatore.
I controller erano quindi inizialmente stati pensati in modo differente. Modificando sensibilmente la struttura del pattern MVC si puntava a far diventare i Controller degli Attori.

<div align="center">
  <img src="https://images2.imgbox.com/4e/9a/SU8kXMWy_o.png" alt="Struttura degli attori">
  <p align="center">Struttura degli attori</p>
</div>

Nella struttura presentata _GameMasterController_ diventa un attore e tutti i sub controller diventano attori figli.
Alcuni benefici di questa modellazione:
- Una volta istanziati gli attori è possibile per ognuno di loro dialogare con altri attori del sistema conoscendo il loro identificativo in modo trasparente senza passare tramite l'attore padre.
- Un'eventuale estensione del gioco nel distribuito sarebbe probabilmente più facilmente sviluppabile.
- È possibile avere molta più varietà sui contenuti che i controller possono mandarsi tra loro; molto utile nel caso in cui i controller abbiano bisogno di coordinarsi tra loro e prendere decisioni in funzione dello stato degli altri.
> **_Un esempio_** :  
durante una battaglia è possibile utilizzare oggetti all'interno dell'inventario. Esistono quindi due controller, _InventoryController_ e _BattleController_ che dialogano tra loro. Grazie al modello ad attori _BattleController_ potrebbe capire se è stato utilizzato uno strumento o no in base al tipo o al contenuto di un messaggio inviat da _InventoryController_.

Nelle fasi iniziali del progetto sono stati realizzati alcuni prototipi dell'applicazione aventi questa struttura e sono stati riscontrate le seguenti criticità:

- Trasformare i Controller in attori significherebbe trasformare anche le View in attori, facendo venire ancora meno la struttura del pattern MVC.
- Il comportamento dei Controller dipenderebbe fortemente dal tipo di messaggi che può ricevere. Questo tipo di architettura è particolarmente scalabile, ma mano a mano che il sistema cresce tende anche ad essere particolarmente dispersiva. Il numero di tipi di messaggi all'interno del sistema sarebbe molto elevato e la navigazione del codice di conseguenza più tediosa.
- La natura dell'applicazione non richiede mai che più di un attore esegua il suo comportamento in contemporanea ad altri.
- Il modello a scambio di messaggi pecca in performance: inviare messaggi è generalmente un'operazione più lenta rispetto alla chiamata di metodo.

A seguito di questa analisi è stato unimamente deciso di non utilizzare il framework Akka e di seguire il pattern MVC.
### 3.3.3 - Serializzazione e deserializzazione tramite librerie json
Le specifiche del progetto richiedono il salvataggio di alcune informazioni su dei supporti di memorizzazione, presumibilmente file.  
L'idea iniziale è stata quella di utilizzare il linguaggio json come standard per la memorizzazione di contenuti su file.

Alcuni benefici di questa scelta:
- Modificare o addirittura creare storie per l'applicazione risulterebbe molto più semplice.
- Il linguaggio json è largamente utilizzato non solo in ambito web.
- L'utilizzo di questo standard renderebbe più semplice l'eventuale estensione del progetto nell'ambito web.

Sono tuttavia state riscontrate anche le seguenti criticità:
- Alcune delle librerie più famose per quanto riguarda la serializzazione e deserializzazione di classi Java (per esempio gson) non sono completamente compatibili con il linguaggio Scala.
- Il linguaggio json non è fortemente tipato, sono quindi richiesti dei controlli in più.
- La serializzazione e deserializzazione di strutture più complesse (come funzioni e stretegie) è difficilmente rappresentabile in json.

Si è quindi optato per un approccio più semplice anche se meno flessibile.
Grazie all'utilizzo della trait [Serializable](https://www.scala-lang.org/api/2.12.5/scala/Serializable.html) del linguaggio Scala è possibile serializzare facilmente quasi ogni classe. Anche classi che non era possibile serializzare in Java (come ad esempio [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)) sono invece completamente supportate per questo genere di operazione su Scala ([Option](https://www.scala-lang.org/api/current/scala/Option.html) è serializzabile). È tuttavia importante ricordare che esiste una perdita di flessibilità perché modifiche alle strutture serializzabili possono essere causa di incompatibilità per oggetti serializzati precedentemente.

---