# Design di dettaglio

## Scelte rilevanti

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

### Adapter pattern
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


<!-- etc. -->

---