package barqsoft.footballscores;


import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static String getLeague(int league_num,Context mcontext)
    {
        switch (league_num)
        {
            // TODO: these are untranslateable
            case SERIE_A : return  mcontext.getString(R.string.SeriaLeage);
            case PREMIER_LEGAUE : return mcontext.getString(R.string.PremierLeague);
            case CHAMPIONS_LEAGUE : return  mcontext.getString(R.string.UEFAChampionsLeague);
            case PRIMERA_DIVISION : return  mcontext.getString(R.string.PrimeraDivisionLeauge);
            case BUNDESLIGA : return  mcontext.getString(R.string.BundesligaLeague);
            default: return  mcontext.getString(R.string.UnknownLeague);
        }
    }
    public static String getMatchDay(int match_day,int league_num,Context mcontext)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            // TODO: These are untranslatable
            if (match_day <= 6)
            {
                return mcontext.getString(R.string.GroupStageMatchDay);

            }
            else if(match_day == 7 || match_day == 8)
            {
                return mcontext.getString(R.string.FirstKnockoutRoundMatchDay);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return mcontext.getString(R.string.QuarterFinalMatchDay);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return mcontext.getString(R.string.MatchdaySemiFinal);
            }
            else
            {
                return mcontext.getString(R.string.FinalMatchDay);
            }
        }
        else
        {
            return mcontext.getString(R.string.NumberMatchday) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals,Context mcontext)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return mcontext.getString(R.string.dashWithSpace);
        }
        else
        {
            return String.valueOf(home_goals) + mcontext.getString(R.string.dashWithSpace) + String.valueOf(awaygoals);
        }
    }

    public static int positionForRTL(int position, int total) {
        return total - position - 1;
    }

    public static int getTeamCrestByTeamName (String teamname)
    {

        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        {
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.football;
        }
    }
}
