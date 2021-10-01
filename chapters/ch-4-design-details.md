# 4 - Design di dettaglio

## Scelte rilevanti

## Stati del sistema
<div align="center">
<img src="https://images2.imgbox.com/73/62/WhxIIroY_o.png" alt="Diagramma di sequenza - esecuzione di una partita">
<p align="center">Diagramma degli stati - menu principale</p>
</div>
All'avvio dell'applicativo il sistema mostrerà il menu principale. Da qui vi è la possibilità di:

- Giocare ad una delle storie disponibili caricando (se possibile) progressi precedentemente svolti all'interno della stessa o iniziando una nuova partita;
- Utilizzare l'editor di storie riprendendo il lavoro da dove lo si era lasciato (ricostruendo il model attraverso un file precedentemente salvato) o cominciando un nuovo editing da zero;
- Aggiungere nuove storie utilizzando i file generati dall'editor;
- Rimuovere storie precedentemente aggiunte.

### Playing
<div align="center">
<img src="https://images2.imgbox.com/c7/07/IQBeAYQ8_o.png" alt="Diagramma di sequenza - in gioco">
<p align="center">Diagramma degli stati - in gioco</p>
</div>
Avviata l'esplorazione di una storia, innanzitutto il sistema permetterà all'utente di creare un personaggio da impersonare all'interno dell'avventura (solo se la partita è iniziata senza caricare progressi predenti).  

Caricato il nodo corrente, viene innanzitutto controllato se questo contiene un nemico; in caso affermativo verrà avviata una battaglia.  

Nel caso in cui non vi sia stata una battaglia o il giocatore sia uscito vittorioso da quest'ultima, verrà controllato se il nodo corrente contiene degli eventi. Nel caso vi siano degli eventi da svolgere essi verranno eseguiti in sequenza.  

Una volta gestiti gli eventuali eventi verranno presentate all'utente le varie strade che egli potrà intraprendere partendo da questo nodo (oltre che la possibilità di accedere all'inventario, salvare i progressi, visualizzare le statistiche, ecc...); saranno visualizzate solo le strade che soddisfino eventuali condizioni imposte sulle stesse.  

Scelta la strada attraverso la quale continuare la storia, viene caricato un nuovo StoryNode, ripetendo quanto descritto precedentemente.  

Tale ciclo continuerà finchè non si verificherà una di queste tre condizioni:
- Viene raggiunto un nodo finale (cioè che non contiene alcuna strada percorribile);
- Il giocatore viene sconfitto in una battaglia;
- L'utente sceglie di uscire dalla partita.

Vediamo ora nel dettaglio lo stato "in a battle" (di complessità maggiore rispetto agli altri stati composti).

### In a battle
<div align="center">
<img src="https://images2.imgbox.com/97/ce/RABb77Vp_o.png" alt="Diagramma di sequenza - in battaglia">
<p align="center">Diagramma degli stati - in battaglia</p>
</div>
All'interno di una battaglia l'utente è posto davanti a quattro possibili scelte:
- Uscire dal gioco
- Utilizzare un oggetto 
L'utente, attraverso l'inventario (opportunamente filtrato per mostrare solo oggetti utilizzabili in battaglia), utilizza un oggetto. Successivamente a tale azione, il nemico attacca il giocatore. 
- Tentare la fuga
Il giocatore sceglie la fuga che, però, non è garantita. Attraverso un algoritmo (che utilizza le caratteristiche dei due personaggi sfidanti), viene determinato se il tentativo di fuga ha avuto successo (nel caso di insuccesso il giocatore verrà colpito dal nemico).
- Attaccare il nemico
In questo caso, un algoritmo (di natura simile al precedente), determina l'ordine in cui avverranno gli attacchi dei due personaggi coinvolti in battaglia. Verranno dunque gestite l'eventuale morte del giocatore (game over) o del nemico (vittoria).

### Using editor
<div align="center">
<img src="https://images2.imgbox.com/dc/ed/YvFHrJgV_o.png" alt="Diagramma di sequenza - editor">
<p align="center">Diagramma degli stati - editor</p>
</div>
L'editor presenta lo stesso comportamento per tutte le operazioni possibili (con diversi effetti sul model in base all'operazione selezionata).  

Viene dinamicamente generato un form finalizzato all'inserimento dei dati necessari al compimento dell'operazione; nel momento in cui l'utente desideri confermare l'inserimento dei dati questi ultimi vengono valutati e, in caso positivo, il model viene aggiornato (così come la rappresentazione grafica di tale model). 

## Scelte Rilevanti (Model)

<div align="center">
<img src="https://images2.imgbox.com/09/fd/EfyJaqYw_o.png" alt="Diagramma delle classi - Model">
<p align="center">Diagramma delle classi - Model</p>
</div>
Nelle prossime sezioni verranno approfondite delle sotto parti specifiche del Model e le motivazioni alla base delle scelte adottate.

#### Character

<div align="center">
<img src="https://images2.imgbox.com/85/ab/DVS26CFp_o.png" alt="Diagramma delle classi - Sezione Character">
<p align="center">Diagramma delle classi - Sezione Character</p>
</div>

Un _Character_ rappresenta un personaggio all'interno di una storia.
Esistono due possibili implementazioni di _Character_:
- __Player__: Rappresenta l'utente reale, ne esiste solo uno all'interno di una partita.
- __Enemy__: Rappresenta un avversario che si può incontrare all'interno di una storia.

Ogni _Character_ è definito da alcune caratteristiche:
- un nome;
- un inventario, ovvero una collezione di _Item_;
- un equipaggiamento, ovvero una collezione di _EquipItem_ (gli oggetti possono essere equipaggiati solo se si trovano all'interno dell'inventario).

Inoltre ogni _Character_ possiede delle proprietà, le quali sono racchiuse all'interno di un componente wrapper denominato _PropertiesContainer_, al cui interno sono presenti:

- Un ulteriore componente utilizzato per la gestione dei punti vita, denominato _Health_, il quale contiene:
  * Il valore massimo di punti vita del personaggio;
  * Il valore corrente di punti vita del personaggio;
- Le _Stat_, ovvero delle statistiche che influiscono sulla storia in base al loro valore;
- Gli _StatModifier_, ovvero dei modificatori che influenzano sui valori delle statistiche originali.

#### Stat
<div align="center">
<img src="https://images2.imgbox.com/a4/e9/RnHNUk4O_o.png" alt="Diagramma delle classi - Sezione Stat">
<p align="center">Diagramma delle classi - Sezione Stat</p>
</div>

L'interfaccia _StatDescriptor_ rappresenta un construtto comune per le implementazioni che agiscono sulle statistiche:
- __Stat__: Rappresenta la statistica di un Character.
- __StatModifier__: Rappresenta il modificatore di una statistica, modificando le _Stat_ tramite una strategy.
  
Attualmente esistono sei tipi di statistiche (_StatName_) ed ogni Character deve obbligatoriamente possedere una _Stat_ per ognuna:
- Charisma
- Constitution
- Dexterity
- Intelligence
- Strength
- Wisdom
  
Ciò però non vincola però l'aggiunta o la rimozioni di nuove statistiche nel sistema.

#### Item

<div align="center">
<img src="https://images2.imgbox.com/38/2b/VMq0ckAG_o.png" alt="Diagramma delle classi - Sezione Item">
<p align="center">Diagramma delle classi - Sezione Item</p>
</div>

L'interfaccia Item rappresenta uno strumento all'interno di una storia.  
Un Character può possedere zero o tanti Item che possono essere utilizzati durante la navigazione della storia o in battaglia.
Esistono tre possibili implementazioni dell'interfaccia:

- __EquipItem__: Uno strumento che può essere equipaggiato. Un _Character_ può equipaggiare diversi _EquipItem_, ma solo se sono di tipi (_EquipItemType_) diversi (non si possono per esempio equipaggiare due _EquipItem_ di tipo _Weapon_ contemporaneamente). Un EquipItem applica degli _StatModifier_ al Character che lo equipaggia, influenzando quindi le sue statistische;
- __ConsumableItem__: Uno strumento a singolo utilizzo. Una volta utilizzato verrà rimosso dall'inventario del possessore. L'oggetto ha un effetto sul bersaglio che viene scelto tramite l'interfaccia _OnConsume_, che rappresenta la strategy da applicare;
- __KeyItem__: Uno strumento che non si può utilizzare, ma può essere richiesta la sua presenza all'interno dell'inventario per soddisfare dei Prerequisite all'interno della storia.

La classe __AbstractItem__ è una classe astratta utilizzata per definire la struttura del metodo _use()_, il quale diventa un template method diviso in due fasi:
1. Tramite il metodo _applyEffect()_ si applicano gli effetti dello strumento;
2. Tramite il metodo _postEffect()_ si specifica cosa accade una volta che gli effetti sono stati applicati.

#### Event

<div align="center">
<img src="https://images2.imgbox.com/b3/11/Ih55E50Y_o.png" alt="Diag ramma delle classi - Sezione Event">
<p align="center">Diagramma delle classi - Sezione Event</p>
</div>

La classe _Event_ rappresenta un'evento che può essere contenuto all'interno di uno _StoryNode_. Quando si accede ad uno _StoryNode_ vengono eseguiti tutti gli eventi contenuti tramite il rispettivo metodo _handle()_, che può potenzialmente agire su ogni aspetto dello _StoryModel_ corrente.
Gli eventi sono irreversibili e non si può evitare che vengano eseguiti se si entra nello _StoryNode_ che li contiene.
Sono state realizzate due implementazioni:
- __StatEvent__: Un evento che influenza le statistiche del _Player_ tramite uno _StatModifier_ (es: "Sei inciampato cercando di fuggire" [-5 Dexterity])
- __ItemEvent__: Un evento che inserisce nell'inventario del _Player_ uno strumento. (es: "Hai trovato il Sacro Graal")

#### Storia 
<div align="center">
<img src="https://images2.imgbox.com/b5/35/UqT5Vidt_o.png" alt="Diagramma delle classi - Sezione Storia">
<p align="center">Diagramma delle classi - Sezione Storia</p>
</div>

La struttura principale da cui è composta una storia è _StoryNode_.  
Uno _StoryNode_ rappresenta un nodo all'interno della storia, in cui il giocatore legge gli avvenimenti (denominati _narrative_) e sceglie di conseguenza un _Pathway_ che lo porterà in un altro _StoryNode_.  
Lo _StoryNode_ è definito univocamente da un ID numerico non visibile durante il gioco e può opzionalmente contenere degli eventi ed un nemico.  
  
Con _Pathway_ definiamo il percorso da uno _StoryNode_ al successivo; la descrizione esprime l'azione che si è deciso di intraprendere.  
Non tutti i _Pathway_ sono sempre visibili all'utente mentre gioca: alcuni compaiono solo se un determinato _Prerequisite_ è soddisfatto.
_Prerequisite_ può potenzialmente agire su ogni aspetto dello _StoryModel_ corrente. 
   
Le interfacce _MutableStoryNode_ e _MutablePathway_ sono versioni mutabili delle interfacce apena illustrate, utilizzate solo all'interno dell'editor per facilitare le operazioni di creazione di una nuova storia.  

L'interfaccia _StoryModel_ rappresenta il contenitore in cui sono presenti tutte le informazioni utilizzate dai Controller per effettuare operazioni durante una partita.
In particolare contiene:
- Il nodo corrente della storia.
- Una lista di tutti i nodi attraversati fino al nodo corrente.
- Il nome della storia.
- L'istanza di _Player_ che sta giocando.

_Progress_ è una classe che serve per salvare il progresso di una partita. Non tutte le informazioni contenute nello StoryModel sono necessarie, vengono salvati solo:
- Lo stato del _Player_;
- Tutti gli ID dei nodi visitati, in ordine.


## Scelte Rilevanti (Controller)

<div align="center">
<img src="https://images2.imgbox.com/58/e0/Xd6b4LFE_o.png" alt="Diagramma delle classi - Controller">
<p align="center">Diagramma delle classi - Controller</p>
</div>
Nelle prossime sezioni verranno approfondite delle sotto parti specifiche del Controller e le motivazioni alla base delle scelte adottate.

#### Application Controller

<div align="center">
<img src="https://images2.imgbox.com/f9/f5/OYt2n0p5_o.png" alt="Diagramma delle classi - Sezione Menu Principale">
<p align="center">Diagramma delle classi - Sezione Menu Principale</p>
 
#### Game Controller

<div align="center">
<img src="https://images2.imgbox.com/b3/87/K9t2eOcm_o.png" alt="Diagramma delle classi - Sezione di Gioco">
<p align="center">Diagramma delle classi - Sezione di Gioco</p>

#### Editor Controller

<div align="center">
<img src="https://images2.imgbox.com/56/51/GRSsNAHM_o.png" alt="Diagramma delle classi - Sezione Editor">
<p align="center">Diagramma delle classi - Sezione Editor</p>

#### Explorer Controller
<div align="center">
<img src="https://images2.imgbox.com/c2/4a/Z0DYiB5i_o.png" alt="Diagramma delle classi - Sezione Esploratore">
<p align="center">Diagramma delle classi - Sezione Esploratore</p>


## Scelte Rilevanti (View)

## Organizzazione del codice

<!-- NOTA: corredato da pochi ma efficaci diagrammi -->
<!-- Lista dei vari componenti del sistema -->

## Pattern di progettazione
<!-- Lista dei vari pattern utilizzati, perché e dove -->
### Model View Controller (MVC)

### Factory Pattern
Factory è un pattern creazionale fondamentale utile per la creazione di oggetti.  
Nel linguaggio Scala il metodo factory convenzionale è denominato ```apply()```.
Il metodo viene utilizzato all'interno di oggetti denominati _companion object_, cioè oggetti che vengono affiancati ai trait e che proprio grazie al metodo ```apply()``` ritornano determinate instanze di classi sulla base degli argomenti passati.  

In ScalaQuest vengono utilizzati abbondantemente i metodi factory per la creazione di tutti gli oggetti dell'applicazione.

### Strategy Pattern
Il pattern strategy viene utilizzato per isolare un algoritmo (la strategia, la strategy) all'interno di un oggetto.  
Strategy prevede che gli algoritmi siano intercambiabili tra loro, in base ad una specificata condizione, in modalità trasparente al client che ne fa uso.  

ScalaQuest fa uso di pattern Strategy in diversi punti dell'applicazione, per esempio all'interno della classe ```StatModifier```.  
```StatModifier``` è una classe di Model che si occupa di incapsulare un algoritmo che modifica in un certo modo una determinata statistica.  
Un oggetto ```StatModifier``` quindi contiene una funzione che modifica un valore intero di input (la statistica) in un valore intero di output (la statistica con valore aggiornato).  
Nel linguaggio Scala una funzione di questo tipo è facilmente esprimibile grazie al paradigma funzionale:
``` scala
def modifyStrategy: Int => Int
```
### Template Method
Template method è un pattern comportamentale che permette di definire la struttura di un algoritmo lasciando alle sottoclassi il compito di implementarne i passi come preferiscono.  
In questo modo si può ridefinire e personalizzare parte del comportamento nelle varie sottoclassi senza dover specificare ogni volta una struttura comune.  

Nell'applicativo viene fatto uso del pattern Template method per esempio nelle classi che estendono ```AbstractItem```. Il trait ```Item``` possiede un metodo ```use(...)``` che viene subito diviso in due sottoparti: ```applyEffect(...)``` (effettivo uso dello strumento e applicazione dell'effetto) e ```postEffect(...)``` (ciò che accade una volta che lo strumento è stato utilizzato).  
Le sottoclassi di ```AbstractItem``` non dovranno modificare il metodo ```use(...)```, ma sono vincolate a specificare il comportamento delle due sottoparti.
``` scala
def use(): Unit = {
    applyEffect()
    postEffect()
}

// subclasses will specify sub-algorithms
def applyEffect(): Unit 

def postEffect(): Unit
```

### Singleton Pattern
Singleton è un pattern creazionale che punta a far si che, di una data classe, esista una ed una sola istanza globalmente accedibile.  
Nonostante le comodità offerte da questo pattern, può risultare facile abusarne erroneamente; Singleton difatti andrebbe usato solo nei casi in cui si sia certi che non esisterà mai più di un'istanza di una certa classe e mai solo per ottenere un accesso comodo ad un oggetto.  

In ScalaQuest esistono solo due Singleton: l'```ApplicationController``` (in cima alla gerarchia dei controller) e il ```Frame``` (dettato dalla necessità del framework di Swing di mantenere sempre la stessa istanza di Frame per evitare che l'utente veda chiudersi e riaprire la finestra nella GUI).  
``` scala
sealed trait ApplicationController extends Controller {
    ...
}

object ApplicationController extends ApplicationController {
    ...
}
```

### Adapter Pattern
Conosciuto anche con il nome di Wrapper, Adapter è un pattern di design che punta a risolvere un problema di compatibilità tra due oggetti.  
Non è raro che una libreria disponga di una funzionalità utile ma vi sia un problema ad interfacciare i propri dati con quanto richiesto dal framework. Tramite questo pattern viene dunque creato un oggetto che riesca ad adattare i dati di modo da mettere in comunicazione le due interfacce incompatibili.

L'editor fa largo uso della libreria GraphStream, utile per rappresentare a schermo dei grafi contenenti dei nodi; tale framework però è ha un'impostazione diversa rispetto ai nostri StoryNode e Pathway.  
Al fine di poter rappresentare graficamente il nostro model dunque è stata creata la classe ```EdgeInfo``` che sopperisce al problema di incompatibilità dei dati.
``` scala
protected case class EdgeInfo(private val startingNode: StoryNode,
                              private val pathway: Pathway) {

  val isFinalNode: Boolean = pathway.destinationNode.pathways.isEmpty

  val isConditionalEdge: Boolean = pathway.prerequisite.nonEmpty

  ...

}
```

### Proxy Pattern
TODO descrizione proxy

Questo pattern è risultato fondamentale per gestire lo svolgimento del gioco; i vari SubController che si occupano di diversi aspetti relativi alla partita (StoryController, HistoryController, ProgressSaverController, ecc...) infatti sono messi in comunicazione tra loro tramite il ```GameMasterController``` che sfrutta questo pattern esponendo il metodo ```executeOperation``` così che sia possibile in modo facile cambiare quale Controller abbia la parola in quel momento richiamando.

``` scala
private class GameMasterControllerImpl(private val storyModel: StoryModel)
    extends GameMasterController {

    ...

    override def executeOperation(op: OperationType): Unit = op match {
      case OperationType.StoryOperation =>
        subControllersContainer.storyController.execute()
      case OperationType.HistoryOperation =>
        subControllersContainer.historyController.execute()
      ...
    }
  }
```
### Builder Pattern
Il design pattern Builder è un pattern creazionale attraverso il quale semplificare la creazione di un oggetto complesso, rendendo anche possibile creare diverse rappresentazioni di tale oggetto.
L'algoritmo per la creazione di tale oggetto è indipendente dalle varie parti che lo compongono e da come vengono assemblate; ciò ha l'effetto immediato di rendere più semplice la classe, permettendo a una classe builder separata di focalizzarsi sulla corretta costruzione di un'istanza e lasciando che la classe originale si concentri sul funzionamento degli oggetti.  

Come citato precedentemente, i Form dell'editor sono stati costruiti agilmente attraverso il ```FormBuilder```, che permette la creazione di form complessi semplicemente chiamando a catena i suoi metodi per aggiungere campi al form.

``` scala
case class FormBuilder() {

    private val listBuffer: ListBuffer[FormElement] = ListBuffer()

    def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
        addField(TextAreaInputElement(label, oldText))

    def addIntegerField(label: String): FormBuilder =
        addField(IntegerInputElement(label))

    ...

    private def addField(formElement: FormElement): FormBuilder = {
        listBuffer += formElement
        this
    }

  def get(controller: Controller): Form = Form(controller, listBuffer.toList)

}
```
### Facade Pattern
Facade è un design pattern usato per esporre una facciata molto semplice che mascheri un sistema complesso al fine di migliorare la leggibilità e usabilità del codice fornendo un unico punto di accesso.

Il Pattern è stato utilizzato all'interno di ``SqPrologEngine``, ovvero la classe che rappresenta il motore del lingugagio Prolog che si occupa di fornire alcuni fatti in output data una certa richiesta in input tramite il metodo ``resolve``.  
Il funzionamente del sistema è particolarmente complesso, ma l'utilizzatore riesce a fruirne in maniera trasparente. L'utente deve solo saper maneggiare le strutture dati di input e output per utilizzare la classe. Se nel nome della classe non venisse menzionato il linguaggio Prolog, probabilmente l'utilizzatore non sarebbe al corrente che viene utilizzato un altro linguagggio per effettuare le computazioni richieste.

``` scala
case class SqPrologEngine(storyNode: StoryNode) {

  ...

  def resolve[A <: Term](goal: A): Stream[A] = ...

  ...
}
```

### Iterator Pattern
Iterator è un pattern comportamentale che viene utilizzato quando si vuole accedere agli elementi di una collezione senza dover esporne la struttura. L’obiettivo è la creazione di un oggetto che esponga sempre gli stessi metodi indipendentemente dall’aggregato di dati.

Il pattern è stato per esempio utilizzato per il reperimento delle soluzioni nella classe ``SqPrologEngine``.  
L'iteratore in questo caso viene utilizzato per aggregare le diverse soluzioni che la libreria tuProlog restituisce.
``` scala
private case class PrologEngineIterator[A <: Term](engine: Prolog, goal: A) extends Iterator[A] {

    override def hasNext: Boolean = {
      // check if there are pending solutions
    }

    override def next(): A = {
      // return a pending solution
    }

```

<!-- etc. -->

---