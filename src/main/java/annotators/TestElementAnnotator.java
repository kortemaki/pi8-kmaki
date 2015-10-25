package annotators;

import java.util.Arrays;

import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.EmptyFSList;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.NonEmptyFSList;

import type.Passage;
import type.Question;
import type.Span;
import type.TestElementAnnotation;

/**
 * A simple segment annotator for PI4.
 * 
 * Expects a CAS for a document in the below format as input 
 * Creates annotations for the question and its answers as output
 * 
 * This annotator has no configuration parameters
 * 
 * Document Text Format:
 *  QUESTION\nPASSAGE1\nPASSAGE2\nPASSAGE3\n...
 *  
 *  Where QUESTION is of the form
 *  factoid_id QUESTION question_text
 *  
 *  And PASSAGEx is of the form
 *  factoid_id document_id correct/incorrect answer_text
 */


public class TestElementAnnotator extends CasAnnotator_ImplBase 
{
	private static final String WHITESPACE = "\\s";
	
	private static final String QUESTION_MARKER = "QUESTION";
	

	/**
	 * Annotate the test element
	 *  for a single question and its related passages
	 *  
	 *  Assumes the document text is of the form
	 *  QUESTION\nPASSAGE1\nPASSAGE2\nPASSAGE3\n...
	 *  
	 *  Where QUESTION is of the form
	 *  factoid_id QUESTION question_text
	 *  
	 *  And PASSAGEx is of the form
	 *  factoid_id document_id correct/incorrect answer_text
	 */
	public void process(CAS aCas) throws AnalysisEngineProcessException 
	{
		JCas jcas;
		try {
			jcas = aCas.getJCas();
		} catch (CASException e) {
			throw new AnalysisEngineProcessException(e);
		}

		//Extract the relevant spans
		String text = jcas.getDocumentText();
		String[] lines = text.split("\n");
		String question = lines[0];
		
		String[] passages = Arrays.copyOfRange(lines, 1, lines.length);

		////////////////////////////////
		//Annotate test element metadata		
		String qnum = question.split(WHITESPACE)[0];
		TestElementAnnotation te = new type.TestElementAnnotation(jcas); // the test element
		te.setBegin(0);
		te.setEnd(text.length());
		te.setText("QUESTION " + qnum + " + PASSAGES");
		te.setComponentId(this.getClass().getName());
		
		////////////////////////////
		//Annotate the question span
		int index = question.length();
		Question q = new Question(jcas);
		q.setId(qnum);
		q.setBegin(qnum.length() + QUESTION_MARKER.length() + 2);
		q.setEnd(index);
		q.setText(question.substring(q.getBegin()));
		q.setAnalysisAnnotations(new EmptyFSList(jcas));
		q.setComponentId(this.getClass().getName());
		
		///////////////////////
		//Annotate the passages
		FSList tePassages = new EmptyFSList(jcas);
		for(String passage : passages)
		{
			//Annotate the raw passage string
			Passage tePassage = new Passage(jcas); 
			tePassage.setBegin(index + 1);
			index = index + 1 + passage.length();
			tePassage.setEnd(index);
			tePassage.setText(passage);
			
			//Identify and annotate the passage span of interest
			String sourceDocID = passage.split(WHITESPACE)[1];
			String label = passage.split(WHITESPACE)[2];
			int textStart = qnum.length() + sourceDocID.length() + label.length() + 3; 
			Span passageSpan = new Span(jcas);
			passageSpan.setText(passage.substring(textStart));
			passageSpan.setBegin(tePassage.getBegin() + textStart);
			passageSpan.setEnd(passageSpan.getBegin() + passageSpan.getText().length());
			passageSpan.setComponentId(this.getClass().getName());
			passageSpan.addToIndexes();
			
			//Finish up with this passage
			tePassage.setPassage(passageSpan);
			tePassage.setAnalysisAnnotations(new EmptyFSList(jcas));
			tePassage.setSourceDocId(sourceDocID);
			try
			{
				tePassage.setLabel(Integer.parseInt(label) > 0);
			}
			catch(NumberFormatException e)
			{
				tePassage.setLabel(false);
			}
			tePassage.setQuestion(q);
			tePassage.setComponentId(this.getClass().getName());
			tePassage.addToIndexes();
			NonEmptyFSList tepass = new NonEmptyFSList(jcas);
			tepass.setHead(tePassage);
			tepass.setTail(tePassages);
			tePassages = tepass;
		}
		q.setPassages(tePassages);
		q.addToIndexes();
		te.setQuestion(q);
		
		te.addToIndexes();
		System.out.println("    Annotated test element in document " + q.getId() + ".");
	}
}