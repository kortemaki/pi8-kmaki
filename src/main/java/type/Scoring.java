

/* First created by JCasGen Mon Oct 05 10:08:06 EDT 2015 */
package type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;


/** Annotation holding the ranking scores for each of the passages associated with this Test Element.
 * Updated by JCasGen Mon Oct 19 15:25:59 EDT 2015
 * XML source: /media/maki/OS/Users/Keith/Documents/CMU/Coursework/11791/PI7/pi7-kmaki/src/main/resources/descriptors/typeSystem.xml
 * @generated */
public class Scoring extends SpanModification {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Scoring.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Scoring() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Scoring(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Scoring(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Scoring(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: scores

  /** getter for scores - gets The list of scores for each of the respective passages associated with this Test Element.
   * @generated
   * @return value of the feature 
   */
  public FSList getScores() {
    if (Scoring_Type.featOkTst && ((Scoring_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "type.Scoring");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Scoring_Type)jcasType).casFeatCode_scores)));}
    
  /** setter for scores - sets The list of scores for each of the respective passages associated with this Test Element. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setScores(FSList v) {
    if (Scoring_Type.featOkTst && ((Scoring_Type)jcasType).casFeat_scores == null)
      jcasType.jcas.throwFeatMissing("scores", "type.Scoring");
    jcasType.ll_cas.ll_setRefValue(addr, ((Scoring_Type)jcasType).casFeatCode_scores, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    